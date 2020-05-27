package com.lsl.kunmming.community.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lsl.kunmming.community.mapper.QuestionMapper;
import com.lsl.kunmming.community.mapper.UserMapper;
import com.lsl.kunmming.community.pojo.PaginationDTO;
import com.lsl.kunmming.community.pojo.Question;
import com.lsl.kunmming.community.pojo.QuestionDTO;
import com.lsl.kunmming.community.pojo.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size * (page - 1);
        List<Question> questionlist = questionMapper.selectPageQuestion(offset, size);
        //查询出所有question列表
        Integer totalCount = questionMapper.selectAllQuestion();

        List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
        for (Question question : questionlist) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOS.add(questionDTO);

        }
        PaginationDTO paginationDTO = new PaginationDTO();
        //把实际数据放进要放回的对象
        paginationDTO.setQuestionDTOS(questionDTOS);
        paginationDTO.setPagenation(totalCount,page,size);
        return paginationDTO;

    }
}