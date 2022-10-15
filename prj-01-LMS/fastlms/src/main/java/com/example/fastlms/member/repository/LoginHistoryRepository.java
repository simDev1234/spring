package com.example.fastlms.member.repository;

import com.example.fastlms.member.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {

    List<LoginHistory> findByUserIdOrderByLastLoginDateDesc(String userId);
}
