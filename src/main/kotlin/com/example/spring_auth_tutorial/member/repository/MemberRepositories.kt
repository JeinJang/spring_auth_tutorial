package com.example.spring_auth_tutorial.member.repository

import com.example.spring_auth_tutorial.member.entity.Member
import com.example.spring_auth_tutorial.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository: JpaRepository<MemberRole, Long>