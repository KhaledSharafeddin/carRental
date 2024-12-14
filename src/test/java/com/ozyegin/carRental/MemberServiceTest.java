package com.ozyegin.carRental;

import com.ozyegin.carRental.dto.MemberOutputDTO;
import com.ozyegin.carRental.model.Member;
import com.ozyegin.carRental.repository.MemberRepository;
import com.ozyegin.carRental.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();
    }

    @Test
    void testCreateMember() {
        Member member = new Member();
        member.setName("Asym Hyder");

        MemberOutputDTO result = memberService.createMember(member);

        assertNotNull(result);
        assertNotNull(result.getId()); // Ensure ID is set
        assertEquals("Asym Hyder", result.getName());
    }

    @Test
    void testGetMemberById() {
        Member member = new Member();
        member.setName("Asym Hyder");

        member = memberRepository.save(member);
        Optional<MemberOutputDTO> result = memberService.getMemberById(member.getId());

        assertTrue(result.isPresent());
        assertEquals(member.getId(), result.get().getId());
        assertEquals("Asym Hyder", result.get().getName());
    }

    @Test
    void testGetAllMembers() {
        Member member1 = new Member();
        member1.setName("Asym Hyder");

        Member member2 = new Member();
        member2.setName("Khalid");

        memberRepository.saveAll(Arrays.asList(member1, member2));
        List<MemberOutputDTO> result = memberService.getAllMembers();

        assertEquals(2, result.size());
        assertEquals(member1.getId(), result.get(0).getId());
        assertEquals("Asym Hyder", result.get(0).getName());
        assertEquals(member2.getId(), result.get(1).getId());
        assertEquals("Khalid", result.get(1).getName());
    }

    @Test
    void testUpdateMember() {
        Member existingMember = new Member();
        existingMember.setName("Asym Hyder");

        existingMember = memberRepository.save(existingMember);

        Member updatedMember = new Member();
        updatedMember.setName("Umair");
        updatedMember.setAddress("yurdu");
        updatedMember.setEmail("umair.ahmad@ozu.edu.com");
        updatedMember.setPhone("905095095669");
        updatedMember.setDrivingLicense("License1234");

        MemberOutputDTO result = memberService.updateMember(existingMember.getId(), updatedMember);

        assertNotNull(result);
        assertEquals("Umair", result.getName());
        assertEquals("yurdu", result.getAddress());
        assertEquals("umair.ahmad@ozu.edu.com", result.getEmail());
        assertEquals("905095095669", result.getPhone());
        assertEquals("License1234", result.getDrivingLicense());
    }
}