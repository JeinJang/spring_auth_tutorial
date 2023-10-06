package com.example.spring_auth_tutorial.member.service

import com.example.spring_auth_tutorial.common.exception.InvalidInputException
import com.example.spring_auth_tutorial.member.dto.MemberDtoRequest
import com.example.spring_auth_tutorial.member.entity.Member
import com.example.spring_auth_tutorial.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
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

        return "회원가입이 완료되었습니다."
    }
}