package net.zero.cloud.common.permission;

import lombok.Data;

import java.util.List;

@Data
public class PermissionInfo {
    String uri;
    List<String> strategyNames;
}
