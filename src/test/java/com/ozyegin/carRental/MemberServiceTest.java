package com.ozyegin.carRental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.repository.MemberRepository;
import com.ozyegin.carRental.service.MemberService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class MemberServiceTest {

    private MemberService memberService;
    private MemberRepository memberRepository;
    @BeforeEach
    void setUp() {
        memberRepository = Mockito.mock(MemberRepository.class);
        memberService = new MemberService();
    }
    @Test
    void testCreateMember() {
        Member member = new Member();
        member.setId(1);
        member.setName("Asym Hyder");

        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(member);
        Member result = memberService.createMember(member);
        Mockito.verify(memberRepository).save(any(Member.class));

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Asym Hyder", result.getName());
    }
    @Test
    void testGetMemberById() {
        Member member = new Member();
        member.setId(1);
        member.setName("Asym Hyder");

        Mockito.when(memberRepository.findById(eq(1))).thenReturn(Optional.of(member));
        Optional<Member> result = memberService.getMemberById(1);

        Mockito.verify(memberRepository).findById(eq(1));
        assertTrue(result.isPresent());
        assertEquals(1, result.get().getId());
        assertEquals("Asym Hyder", result.get().getName());
    }

    @Test
    void testGetAllMembers() {
        Member member1 = new Member();
        member1.setId(1);
        member1.setName("Asym Hyder");

        Member member2 = new Member();
        member2.setId(2);
        member2.setName("Khalid");

        List<Member> members = Arrays.asList(member1, member2);
        Mockito.when(memberRepository.findAll()).thenReturn(members);
        List<Member> result = memberService.getAllMembers();

        Mockito.verify(memberRepository).findAll();
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals("Asym Hyder", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
        assertEquals("Khalid", result.get(1).getName());
    }

    @Test
    void testUpdateMember() {
        Member existingMember = new Member();
        existingMember.setId(1);
        existingMember.setName("Asym Hyder");

        Member updatedMember = new Member();
        updatedMember.setName("Umair");
        updatedMember.setAddress("yurdu");
        updatedMember.setEmail("umair.ahmad@ozu.edu.com");
        updatedMember.setPhone("905095095669");
        updatedMember.setDrivingLicense("License1234");

        Mockito.when(memberRepository.findById(eq(1))).thenReturn(Optional.of(existingMember));
        Mockito.when(memberRepository.save(any(Member.class))).thenReturn(existingMember);

        Member result = memberService.updateMember(1, updatedMember);

        Mockito.verify(memberRepository).findById(eq(1));
        Mockito.verify(memberRepository).save(any(Member.class));
        assertNotNull(result);
        assertEquals("Umair", result.getName());
        assertEquals("yurdu", result.getAddress());
        assertEquals("umair.ahmad@ozu.edu.com", result.getEmail());
        assertEquals("905095095669", result.getPhone());
        assertEquals("License1234", result.getDrivingLicense());
    }
}