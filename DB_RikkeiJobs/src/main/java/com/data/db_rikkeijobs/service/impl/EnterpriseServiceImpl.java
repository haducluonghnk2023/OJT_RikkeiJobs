package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.Enterprise;
import com.data.db_rikkeijobs.repository.EnterpriseRepository;
import com.data.db_rikkeijobs.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {
    
    @Autowired
    private EnterpriseRepository enterpriseRepository;
    
    @Override
    public List<Enterprise> findAll() {
        return enterpriseRepository.findAll();
    }
    
    @Override
    public Optional<Enterprise> findById(Long id) {
        return enterpriseRepository.findById(id);
    }
    
    @Override
    public Optional<Enterprise> findByUserId(Long userId) {
        return enterpriseRepository.findByUserId(userId);
    }
    
    @Override
    public List<Enterprise> findByStatus(String status) {
        return enterpriseRepository.findByStatus(status);
    }
    
    @Override
    public Enterprise save(Enterprise enterprise) {
        return enterpriseRepository.save(enterprise);
    }
    
    @Override
    public Enterprise update(Long id, Enterprise enterprise) {
        Optional<Enterprise> existing = enterpriseRepository.findById(id);
        if (existing.isPresent()) {
            Enterprise toUpdate = existing.get();
            toUpdate.setTitle(enterprise.getTitle());
            toUpdate.setEmail(enterprise.getEmail());
            toUpdate.setCompanySize(enterprise.getCompanySize());
            toUpdate.setPhoneNumber(enterprise.getPhoneNumber());
            toUpdate.setIndustry(enterprise.getIndustry());
            toUpdate.setIntroduction(enterprise.getIntroduction());
            toUpdate.setWebsiteUrl(enterprise.getWebsiteUrl());
            toUpdate.setFacebookUrl(enterprise.getFacebookUrl());
            toUpdate.setLinkedinUrl(enterprise.getLinkedinUrl());
            toUpdate.setTwitterUrl(enterprise.getTwitterUrl());
            toUpdate.setBusinessLicense(enterprise.getBusinessLicense());
            toUpdate.setAddress(enterprise.getAddress());
            toUpdate.setUserId(enterprise.getUserId());
            toUpdate.setAvatar(enterprise.getAvatar());
            toUpdate.setStatus(enterprise.getStatus());
            return enterpriseRepository.save(toUpdate);
        }
        throw new RuntimeException("Enterprise not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        enterpriseRepository.deleteById(id);
    }
}

