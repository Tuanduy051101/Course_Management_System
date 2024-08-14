package com.company.projects.course.coursemanagementsystem.mapper;

import com.company.projects.course.coursemanagementsystem.dto.UserDto;
import com.company.projects.course.coursemanagementsystem.model.UserEntity;
import com.company.projects.course.coursemanagementsystem.util.MapperUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserMapper implements EntityMapper<UserEntity, UserDto> {
    @Nonnull MapperUtil mapperUtil;
    @Nonnull AccountMapper accountMapper;
    @Nonnull AssignmentMapper assignmentMapper;

    @Nullable
    public UserEntity toEntity(@Nullable UserDto userDto) {
        if (userDto == null) return null;
        return UserEntity.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .gender(userDto.getGender())
                .birthday(userDto.getBirthday())
                .phone(userDto.getPhone())
                .email(userDto.getEmail())
                .address(userDto.getAddress())
//                .accounts(mapperUtil.mapCollection(userDto.getAccounts(), accountMapper::toEntity))
//                .assignments(mapperUtil.mapCollection(userDto.getAssignments(), assignmentMapper::toEntity))
                .build();
    }

    @Nullable
    public UserDto toDto(@Nullable UserEntity userEntity) {
        if (userEntity == null) return null;
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .gender(userEntity.getGender())
                .birthday(userEntity.getBirthday())
                .phone(userEntity.getPhone())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
//                .accounts(mapperUtil.mapCollection(userEntity.getAccounts(), accountMapper::toDto))
//                .assignments(mapperUtil.mapCollection(userEntity.getAssignments(), assignmentMapper::toDto))
                .build();
    }
}

