package com.ozyegin.carRental.service;

import com.ozyegin.carRental.dto.MemberOutputDTO;
import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberService {
    @Autowired
    private MemberRepository memberRepository;

    public MemberOutputDTO createMember(Member memberInputDTO) {
        Member member = new Member();
        member.setName(memberInputDTO.getName());
        member.setAddress(memberInputDTO.getAddress());
        member.setEmail(memberInputDTO.getEmail());
        member.setPhone(memberInputDTO.getPhone());
        member.setDrivingLicense(memberInputDTO.getDrivingLicense());
        member = memberRepository.save(member);
        return mapToOutputDTO(member);
    }

    public Optional<MemberOutputDTO> getMemberById(Integer id) {
        return memberRepository.findById(id).map(this::mapToOutputDTO);
    }

    public List<MemberOutputDTO> getAllMembers() {
        return memberRepository.findAll().stream().map(this::mapToOutputDTO).collect(Collectors.toList());
    }

    public MemberOutputDTO updateMember(Integer id, Member updatedMemberInputDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        member.setName(updatedMemberInputDTO.getName());
        member.setAddress(updatedMemberInputDTO.getAddress());
        member.setEmail(updatedMemberInputDTO.getEmail());
        member.setPhone(updatedMemberInputDTO.getPhone());
        member.setDrivingLicense(updatedMemberInputDTO.getDrivingLicense());
        member = memberRepository.save(member);
        return mapToOutputDTO(member);
    }

    private MemberOutputDTO mapToOutputDTO(Member member) {
        MemberOutputDTO memberOutputDTO = new MemberOutputDTO();
        memberOutputDTO.setId(member.getId());
        memberOutputDTO.setName(member.getName());
        memberOutputDTO.setAddress(member.getAddress());
        memberOutputDTO.setEmail(member.getEmail());
        memberOutputDTO.setPhone(member.getPhone());
        memberOutputDTO.setDrivingLicense(member.getDrivingLicense());
        return memberOutputDTO;
    }
}