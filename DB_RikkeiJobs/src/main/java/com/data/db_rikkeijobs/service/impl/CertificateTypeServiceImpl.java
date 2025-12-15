package com.data.db_rikkeijobs.service.impl;

import com.data.db_rikkeijobs.entity.CertificateType;
import com.data.db_rikkeijobs.entity.CertificateTypeValue;
import com.data.db_rikkeijobs.repository.CertificateTypeRepository;
import com.data.db_rikkeijobs.service.CertificateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CertificateTypeServiceImpl implements CertificateTypeService {
    
    @Autowired
    private CertificateTypeRepository certificateTypeRepository;
    
    @Override
    public List<CertificateType> findAll() {
        return certificateTypeRepository.findAll();
    }
    
    @Override
    public Optional<CertificateType> findById(Long id) {
        return certificateTypeRepository.findById(id);
    }
    
    @Override
    public CertificateType save(CertificateType certificateType) {
        return certificateTypeRepository.save(certificateType);
    }
    
    @Override
    public CertificateType update(Long id, CertificateType certificateType) {
        Optional<CertificateType> existing = certificateTypeRepository.findById(id);
        if (existing.isPresent()) {
            CertificateType toUpdate = existing.get();
            toUpdate.setType(certificateType.getType());
            toUpdate.setLanguage(certificateType.getLanguage());
            toUpdate.setStatus(certificateType.getStatus());
            toUpdate.setCode(certificateType.getCode());
            
            // Update values list - clear existing and set new ones
            if (certificateType.getValues() != null) {
                toUpdate.getValues().clear();
                // Copy values from the input certificateType and set reference
                List<CertificateTypeValue> newValues = certificateType.getValues().stream()
                        .map(value -> {
                            CertificateTypeValue newValue = new CertificateTypeValue();
                            newValue.setValue(value.getValue());
                            newValue.setCertificateType(toUpdate);
                            return newValue;
                        })
                        .collect(Collectors.toList());
                toUpdate.getValues().addAll(newValues);
            }
            
            return certificateTypeRepository.save(toUpdate);
        }
        throw new RuntimeException("CertificateType not found with id: " + id);
    }
    
    @Override
    public void deleteById(Long id) {
        certificateTypeRepository.deleteById(id);
    }
}

