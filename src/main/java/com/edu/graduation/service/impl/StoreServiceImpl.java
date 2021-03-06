package com.edu.graduation.service.impl;

import com.edu.graduation.dao.BusinessUserMapper;
import com.edu.graduation.dao.StoreMapper;
import com.edu.graduation.entity.bean.BusinessUser;
import com.edu.graduation.entity.bean.Store;
import com.edu.graduation.entity.dto.ModifySelfDTO;
import com.edu.graduation.enums.BackMessageEnum;
import com.edu.graduation.enums.MyExceptionEnum;
import com.edu.graduation.exception.MyException;
import com.edu.graduation.service.StoreService;
import com.edu.graduation.utils.ResultVoUtil;
import com.edu.graduation.vo.ResultVo;
import com.edu.graduation.vo.SelectStoreVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private BusinessUserMapper businessUserMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public ResultVo getStoreData(String userId) {
        List<Store> storeList = new ArrayList<>();
        Store store = storeMapper.getOneByUserId(userId);
        storeList.add(store);
        return ResultVoUtil.success(storeList);
    }

    @Override
    public ResultVo modifyPassword(String userId,String newPassWord,String oldPassword) {
        BusinessUser businessUser = businessUserMapper.getUserById(userId);
        if(passwordEncoder.matches(oldPassword,businessUser.getPassword())){
            int flag = businessUserMapper.updatePw(userId,passwordEncoder.encode(newPassWord));

            if(flag == 1)
                return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
            else
                throw new MyException(MyExceptionEnum.SQL_ERROR);
        }else{
            throw new MyException(MyExceptionEnum.OLD_PASSWORD_ERROR);
        }
    }

    @Override
    public ResultVo modifyData(ModifySelfDTO modifySelfDTO) {
        int flag = storeMapper.updateAllData(modifySelfDTO);
        if(flag == 1)
            return ResultVoUtil.success(BackMessageEnum.MODIFY_SUCCESS.getMessage());
        else
            return ResultVoUtil.success(MyExceptionEnum.SQL_ERROR.getMessage());
    }

    @Override
    public ResultVo getAllStores() {
        List<SelectStoreVo> selectStoreVoList = new ArrayList<>();
        List<Store> storeList = storeMapper.getAllStores();
        for (Store store:storeList) {
            String temp = "门店："+store.getStoreName()+" 地址："+store.getStoreAddress();
            SelectStoreVo selectStoreVo = new SelectStoreVo(store.getStoreId(),temp);
            selectStoreVoList.add(selectStoreVo);

        }
        return ResultVoUtil.success(selectStoreVoList);
    }


}
