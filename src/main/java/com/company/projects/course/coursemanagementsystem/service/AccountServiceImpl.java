package com.company.projects.course.coursemanagementsystem.service;

import com.company.projects.course.coursemanagementsystem.dto.AccountDto;
import com.company.projects.course.coursemanagementsystem.exception.custom.EmptyResultDataAccessException;
import com.company.projects.course.coursemanagementsystem.exception.custom.EntityNotFoundException;
import com.company.projects.course.coursemanagementsystem.mapper.AccountMapper;
import com.company.projects.course.coursemanagementsystem.model.AccountEntity;
import com.company.projects.course.coursemanagementsystem.repository.AccountRepository;
import com.company.projects.course.coursemanagementsystem.util.PasswordUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    AccountRepository accountRepository;
    AccountMapper accountMapper;

    @Override
    public AccountDto findById(String id) {
        AccountEntity entity = accountRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with id = " + id));
        return accountMapper.toDto(entity);
    }

    @Override
    public Collection<AccountDto> findAll() {
        Collection<AccountEntity> entities = accountRepository.findAllByDeletedFalse();
        if (entities.isEmpty()) throw new EmptyResultDataAccessException("Account is empty");
        return entities.stream().map(accountMapper::toDto).toList();
    }

    @Override
    public AccountDto save(AccountDto dto) {
        AccountEntity entity = accountMapper.toEntity(dto);
        assert entity != null;
        entity.setPassword(PasswordUtil.generatePassword());
        entity.setUsername(dto.getUser().getEmail());

        AccountEntity savedAccount = accountRepository.save(entity);

        return accountMapper.toDto(savedAccount);
    }

    @Override
    public void deleteById(String id) {
        accountRepository.softDeleteById(id);
    }

    @Override
    public AccountDto update(String id, AccountDto dto) {
        return null;
    }

    @Override
    public Collection<AccountDto> searchAllByName(String name) {
        return List.of();
    }

    @Override
    public Collection<AccountDto> searchAllByPhone(String phone) {
        return List.of();
    }

    @Override
    public Collection<AccountDto> searchByEmail(String email) {
        return List.of();
    }
}
