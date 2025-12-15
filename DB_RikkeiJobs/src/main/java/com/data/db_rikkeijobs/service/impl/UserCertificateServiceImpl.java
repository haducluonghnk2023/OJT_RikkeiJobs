package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.UserCertificate;
import com.data.db_rikkeijobs.repository.UserCertificateRepository;
import com.data.db_rikkeijobs.service.UserCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserCertificateServiceImpl implements UserCertificateService {
    
    @Autowired
    private UserCertificateRepository userCertificateRepository;
    
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
}

