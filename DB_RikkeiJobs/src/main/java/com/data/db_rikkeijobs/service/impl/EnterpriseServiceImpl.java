package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.request.UpdateEnterpriseRequest;
import com.data.db_rikkeijobs.dto.response.EnterpriseResponse;
import com.data.db_rikkeijobs.entity.Enterprise;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.EnterpriseMapper;
import com.data.db_rikkeijobs.repository.EnterpriseRepository;
import com.data.db_rikkeijobs.service.EnterpriseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EnterpriseServiceImpl implements EnterpriseService {
    
    @Autowired
    private EnterpriseRepository enterpriseRepository;

    @Autowired
    private EnterpriseMapper enterpriseMapper;
    
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

    @Override
    public List<EnterpriseResponse> getAllEnterpriseResponses() {
        return enterpriseRepository.findAll().stream()
                .map(enterpriseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EnterpriseResponse getEnterpriseResponseById(Long id) {
        return enterpriseRepository.findById(id)
                .map(enterpriseMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
    }

    @Override
    public EnterpriseResponse getEnterpriseResponseByUserId(Long userId) {
        return enterpriseRepository.findByUserId(userId)
                .map(enterpriseMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with user id: " + userId));
    }

    @Override
    public List<EnterpriseResponse> getEnterpriseResponsesByStatus(String status) {
        return enterpriseRepository.findByStatus(status).stream()
                .map(enterpriseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EnterpriseResponse createEnterprise(CreateEnterpriseRequest request) {
        Enterprise enterprise = enterpriseMapper.toEntity(request);
        return enterpriseMapper.toResponse(save(enterprise));
    }

    @Override
    public EnterpriseResponse updateEnterprise(Long id, UpdateEnterpriseRequest request) {
        Enterprise existingEnterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
        enterpriseMapper.updateEntityFromRequest(request, existingEnterprise);
        return enterpriseMapper.toResponse(update(id, existingEnterprise));
    }

    @Override
    public EnterpriseResponse patchEnterprise(Long id, UpdateEnterpriseRequest request) {
        Enterprise existingEnterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("Enterprise not found with id: " + id));
        enterpriseMapper.updateEntityFromRequest(request, existingEnterprise);
        return enterpriseMapper.toResponse(update(id, existingEnterprise));
    }

    @Override
    public void deleteEnterpriseOrThrow(Long id) {
        if (!enterpriseRepository.existsById(id)) {
            throw new HttpNotFound("Enterprise not found with id: " + id);
        }
        enterpriseRepository.deleteById(id);
    }
}

