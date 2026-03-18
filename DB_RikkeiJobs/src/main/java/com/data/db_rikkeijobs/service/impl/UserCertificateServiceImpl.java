package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.dto.request.CreateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.request.UpdateUserCertificateRequest;
import com.data.db_rikkeijobs.dto.response.UserCertificateResponse;
import com.data.db_rikkeijobs.entity.UserCertificate;
import com.data.db_rikkeijobs.exception.HttpNotFound;
import com.data.db_rikkeijobs.mapper.UserCertificateMapper;
import com.data.db_rikkeijobs.repository.UserCertificateRepository;
import com.data.db_rikkeijobs.service.UserCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserCertificateServiceImpl implements UserCertificateService {
    
    @Autowired
    private UserCertificateRepository userCertificateRepository;

    @Autowired
    private UserCertificateMapper userCertificateMapper;
    
    @Override
    public List<UserCertificate> findAll() {
        return userCertificateRepository.findAll();
    }
    
    @Override
    public Optional<UserCertificate> findById(Long id) {
        return userCertificateRepository.findById(id);
    }
    
    @Override
    public List<UserCertificate> findByUserId(Long userId) {
        return userCertificateRepository.findByUserId(userId);
    }
    
    @Override
    public List<UserCertificate> findByCertificateId(Long certificateId) {
        return userCertificateRepository.findByCertificateId(certificateId);
    }
    
    @Override
    public UserCertificate save(UserCertificate userCertificate) {
        return userCertificateRepository.save(userCertificate);
    }
    
    @Override
    public UserCertificate update(Long id, UserCertificate userCertificate) {
        Optional<UserCertificate> existing = userCertificateRepository.findById(id);
        if (existing.isPresent()) {
            UserCertificate toUpdate = existing.get();
            toUpdate.setCertificateType(userCertificate.getCertificateType());
            toUpdate.setCertificateValue(userCertificate.getCertificateValue());
            toUpdate.setReceivedDate(userCertificate.getReceivedDate());
            toUpdate.setExpirationDate(userCertificate.getExpirationDate());
            toUpdate.setUserId(userCertificate.getUserId());
            toUpdate.setCertificateId(userCertificate.getCertificateId());
            return userCertificateRepository.save(toUpdate);
        }
        throw new RuntimeException("UserCertificate not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        userCertificateRepository.deleteById(id);
    }

    @Override
    public List<UserCertificateResponse> getUserCertificateResponses(Long userId, Long certificateId) {
        List<UserCertificate> list;
        if (userId != null) {
            list = userCertificateRepository.findByUserId(userId);
        } else if (certificateId != null) {
            list = userCertificateRepository.findByCertificateId(certificateId);
        } else {
            list = userCertificateRepository.findAll();
        }
        return list.stream().map(userCertificateMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public UserCertificateResponse getUserCertificateResponseById(Long id) {
        return userCertificateRepository.findById(id)
                .map(userCertificateMapper::toResponse)
                .orElseThrow(() -> new HttpNotFound("UserCertificate not found with id: " + id));
    }

    @Override
    public UserCertificateResponse createUserCertificate(CreateUserCertificateRequest request) {
        UserCertificate created = save(userCertificateMapper.toEntity(request));
        return userCertificateMapper.toResponse(created);
    }

    @Override
    public UserCertificateResponse patchUserCertificate(Long id, UpdateUserCertificateRequest request) {
        UserCertificate existing = userCertificateRepository.findById(id)
                .orElseThrow(() -> new HttpNotFound("UserCertificate not found with id: " + id));
        userCertificateMapper.updateEntityFromRequest(request, existing);
        UserCertificate updated = update(id, existing);
        return userCertificateMapper.toResponse(updated);
    }

    @Override
    public void deleteUserCertificateOrThrow(Long id) {
        if (!userCertificateRepository.existsById(id)) {
            throw new HttpNotFound("UserCertificate not found with id: " + id);
        }
        userCertificateRepository.deleteById(id);
    }
}

