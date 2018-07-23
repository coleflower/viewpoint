package com.viewpoint.controller;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.form.ActivityForm;
import com.viewpoint.service.ActivityService;
import com.viewpoint.utils.KeyUtil;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/index")
    public String list(){
        return "intra/activity/list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size,
                         Model model){
        Page<Activity> activityPage = activityService.findAll(PageRequest.of(page - 1 ,size));
        return ResultVOUtil.success(activityPage.getContent(),activityPage.getTotalElements());
    }

    @GetMapping("/add")
    public String add(@RequestParam(value = "activityId",required = false) String activityId ,Model model){
        if (!StringUtils.isEmpty(activityId)) {
            Activity activity = activityService.findOne(activityId);
            model.addAttribute("activity",activity);
        }
        return "intra/activity/newsAdd";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid ActivityForm activityForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        Activity activityInfo = new Activity();
        try {
            //如果productId为空, 说明是新增
            if(!StringUtils.isEmpty(activityForm.getActivityId())){
                activityInfo = activityService.findOne(activityForm.getActivityId());
            }else {
                activityForm.setActivityId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(activityForm, activityInfo);
            activityService.save(activityInfo);
        }  catch (ViewpointException e) {
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    @PostMapping("/updateSale")
    public ResultVO updateSale(String activityId,Integer enabled){
        Activity activity = activityService.updateSale(activityId,enabled);
        return ResultVOUtil.success(activity);
    }
}
