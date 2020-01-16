package com.victoria.bus.service.impl;

import com.victoria.bus.pojo.Customer;
import com.victoria.bus.mapper.CustomerMapper;
import com.victoria.bus.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-01-07
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
