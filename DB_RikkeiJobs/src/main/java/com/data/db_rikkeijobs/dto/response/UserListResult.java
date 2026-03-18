package com.data.db_rikkeijobs.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResult {
    private List<UserResponse> users;
    private int totalCount;
}
