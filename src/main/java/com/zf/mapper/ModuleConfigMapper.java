package com.zf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zf.domain.entity.ModuleConfig;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author pd
 * DateTime: 2022/10/24 14:23
 */
@Repository
public interface ModuleConfigMapper extends BaseMapper<ModuleConfig> {

  void changeModuleSwitch(String userId,int moduleSwitch);
}
