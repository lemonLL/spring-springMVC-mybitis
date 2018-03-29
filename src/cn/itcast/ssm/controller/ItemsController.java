package cn.itcast.ssm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.ssm.po.ItemsCustom;
import cn.itcast.ssm.po.ItemsQueryVo;
import cn.itcast.ssm.service.ItemsService;
import cn.itcast.ssm.validation.ValidGroup1;
@Controller
@RequestMapping("/items")
public class ItemsController {
	@Autowired
	ItemsService itemsService;
	

	//商品分类
	@ModelAttribute("itemtypes")
	public Map<String, String> getItemTypes(){
		
		Map<String, String> itemTypes = new HashMap<String,String>();
		itemTypes.put("101", "数码");
		itemTypes.put("102", "母婴");
		
		return itemTypes;
	}
	
	
	//@RequestMapping("/queryItemsQuery")
	@RequestMapping(value="/queryItemsQuery",method={RequestMethod.GET,RequestMethod.POST})
//	public ModelAndView queryItemsTest() throws Exception{
//		List<ItemsCustom> itemsList = new ArrayList<ItemsCustom>();
//		itemsList = itemsService.findItemList(null);
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("itemsList",itemsList);
//		modelAndView.setViewName("items/itemsList");
//		return modelAndView;
//	}
	
	public String queryItemsTest(Model model,ItemsQueryVo itemsQueryVo) throws Exception{
		List<ItemsCustom> itemsList = new ArrayList<ItemsCustom>();
		itemsList = itemsService.findItemList(itemsQueryVo);
		model.addAttribute("itemsList", itemsList	);
		return "items/itemsList";
	}
	
	//@RequestMapping("/editItems")
	@RequestMapping(value="/editItems",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView editItems(@RequestParam(value="id") Integer item_id ) throws Exception{
		ItemsCustom itemsCustoms = itemsService.findItemsById(item_id);
//		if(itemsCustoms == null){
//			throw new CustomException("无商品信息");
//		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("items", itemsCustoms);
		modelAndView.setViewName("items/editItems");
		return modelAndView;
	}
	
	@RequestMapping("/editItemsSubmit")
//	public ModelAndView editItemsSubmit() throws Exception{
//		ModelAndView modelAndView = new ModelAndView();		
//		modelAndView.setViewName("success");
//		return modelAndView;
//	}
	
	public String editItemsSubmit(Model model,Integer id,@ModelAttribute("items") @Validated(value={ValidGroup1.class}) ItemsCustom itemsCustom,BindingResult result,MultipartFile pictureFile) throws Exception{
		String pictureFileName = pictureFile.getOriginalFilename();
		if(pictureFile!=null && pictureFileName!=null &&pictureFileName.length()>0 && !pictureFileName.equals(itemsCustom.getPic())){
			
			String newFileName = UUID.randomUUID().toString()+pictureFileName.substring(pictureFileName.lastIndexOf("."));
			File newfile = new File("D:\\myworld\\picture\\"+newFileName);
			pictureFile.transferTo(newfile);
			itemsCustom.setPic(newFileName);
		}
		if(result.hasErrors()){
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError objectError:errors){
				System.out.println(objectError.getDefaultMessage());
			}
			model.addAttribute("errors",errors);
			//model.addAttribute("items",itemsCustom);
			return "items/editItems";
		}
		itemsService.updateItems(id, itemsCustom);
//重定向
	//return "redirect:queryItemsQuery.action";
	//return "forward:queryItemsQuery.action";
		return "success";
}
	@RequestMapping("/deleteItems")
	public String deleteItems(Integer[] items_id ){
		//批量删除代码
		return "success";
	}

	//批量编辑页面
	@RequestMapping(value="/queryItemsEdit",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView queryItemsEdit(ItemsQueryVo itemsQueryVo) throws Exception{
		List<ItemsCustom> itemsList = new ArrayList<ItemsCustom>();
		itemsList = itemsService.findItemList(itemsQueryVo);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("itemsList",itemsList);
		modelAndView.setViewName("items/itemsListEdit");
		return modelAndView;
	}
	
	//批量编辑
	@RequestMapping(value="/queryItemsEditOp",method={RequestMethod.GET,RequestMethod.POST})
	public String queryItemsEditOp(ItemsQueryVo itemsQueryVo) throws Exception{

		return "success";
	}
	
	//restful测试
	@RequestMapping("/itemsView/{id}")
	public @ResponseBody ItemsCustom itemsview(@PathVariable("id") Integer id) throws Exception{
		ItemsCustom itemsCustom = itemsService.findItemsById(id);
		return itemsCustom;
	}

}
