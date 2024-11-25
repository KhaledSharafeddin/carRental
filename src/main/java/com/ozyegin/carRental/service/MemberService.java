package com.ozyegin.carRental.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.repository.MemberRepository;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public Member createMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> getMemberById(Integer id) {
        return memberRepository.findById(id);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member updateMember(Integer id, Member updatedMember) {
        return memberRepository.findById(id)
                .map(member -> {
                    member.setName(updatedMember.getName());
                    member.setAddress(updatedMember.getAddress());
                    member.setEmail(updatedMember.getEmail());
                    member.setPhone(updatedMember.getPhone());
                    member.setDrivingLicense(updatedMember.getDrivingLicense());
                    return memberRepository.save(member);
                })
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
    }

}