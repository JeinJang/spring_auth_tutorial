package com.example.spring_auth_tutorial.member.service

import com.example.spring_auth_tutorial.common.authority.JwtTokenProvider
import com.example.spring_auth_tutorial.common.authority.TokenInfo
import com.example.spring_auth_tutorial.common.exception.InvalidInputException
import com.example.spring_auth_tutorial.common.status.ROLE
import com.example.spring_auth_tutorial.member.dto.LoginDto
import com.example.spring_auth_tutorial.member.dto.MemberDtoRequest
import com.example.spring_auth_tutorial.member.entity.Member
import com.example.spring_auth_tutorial.member.entity.MemberRole
import com.example.spring_auth_tutorial.member.repository.MemberRepository
import com.example.spring_auth_tutorial.member.repository.MemberRoleRepository
import jakarta.transaction.Transactional
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    // 회원 가입
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        // ID 중복 검사
        var member: Member? = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member !== null) {
            throw InvalidInputException("loginId", "이미 등록된 ID입니다.")
        }

        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole: MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    // 로그인 -> 토큰 발행
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }
}