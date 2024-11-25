package com.ozyegin.carRental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ozyegin.carRental.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

}
