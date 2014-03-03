package com.darmody.buumanagementsystem.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.darmody.buumanagementsystem.dao.BuuClassDao;
import com.darmody.buumanagementsystem.dao.OfferAnswerDao;
import com.darmody.buumanagementsystem.dao.OfferDao;
import com.darmody.buumanagementsystem.dao.OfferFlowDao;
import com.darmody.buumanagementsystem.dao.StudentDao;
import com.darmody.buumanagementsystem.entity.Administrator;
import com.darmody.buumanagementsystem.entity.BuuClass;
import com.darmody.buumanagementsystem.entity.Student;
import com.darmody.buumanagementsystem.entity.offer.Offer;
import com.darmody.buumanagementsystem.entity.offer.OfferAnswer;
import com.darmody.buumanagementsystem.entity.offer.OfferFlow;
import com.darmody.buumanagementsystem.service.OfferDataManager;
import com.darmody.buumanagementsystem.util.Constraint;
import com.darmody.buumanagementsystem.util.Utility;

/**
 * 2013.9.5
 * @author Caihuanyu
 * @content 报盘数据管理服务接口实现类
 */

@Service
@Transactional(propagation=Propagation.REQUIRED)
public class OfferDataManagerImpl implements OfferDataManager{

	@Resource
	private OfferDao offerDao;
	
	@Resource
	private StudentDao studentDao;
	
	@Resource
	private OfferFlowDao offerFlowDao;
	
	@Resource
	private OfferAnswerDao offerAnswerDao;
	
	@Resource
	private BuuClassDao buuClassDao;

	@Override
	public int addNewOffer(Offer newOffer) {
		
		return this.offerDao.create(newOffer);
	}

	@Override
	public void addOfferFlowsBulk(List<OfferFlow> offerFlows) {

		int fatherKey = -1;
		
		for(int i = 0; i < offerFlows.size(); i++) {
			
			if(offerFlows.get(i).getFlowSpecies().equals("0")) {

				fatherKey = this.offerFlowDao.create(offerFlows.get(i));	
			
			} else {
			
				offerFlows.get(i).setFatherFlowKey(fatherKey);
				
				this.offerFlowDao.create(offerFlows.get(i));
			}
		}
	}

	@Override
	public void initialOffer(Offer installedOffer) {
		
		this.offerDao.update(installedOffer);
	}

	@Override
	public void submitOffer(OfferAnswer answer) {
		
		this.offerAnswerDao.create(answer);
	}

	@Override
	public void checkOffersStatus() {
		
	}

	@Override
	public List<Offer> viewOffersInAdminCharge(Administrator admin) {
		
		List<BuuClass> buuClasses = admin.getChargedClasses();
		
		List<Offer> offers = this.offerDao.getOffers();
		
		if(admin.getAdminType().equals("7")) {
			
			return offers;
		}
		
		List<Offer> resultOffers = new ArrayList<Offer>();
		
		for(Offer offer : offers) {
		
			for(BuuClass theClass : buuClasses) {
				
				int facultyKey = 0;
				
				int majorKey = 0;
				
				int classKey = 0;
				
				if(theClass.getClassType().equals("2")) {
					
					classKey = theClass.getPK_classKey();
					
					majorKey = theClass.getFatherClassKey();
					
					BuuClass faculty = (BuuClass) this.buuClassDao.get(theClass.getFatherClassKey());
					
					if(faculty != null) {
					
						facultyKey = faculty.getFatherClassKey();
					}
						
				} else if(theClass.getClassType().equals("1")) {
	
					majorKey = theClass.getPK_classKey();
					
					facultyKey = theClass.getFatherClassKey();
				
				} else {
					
					facultyKey = theClass.getPK_classKey();
				}
				
				for(BuuClass offerClass : offer.getBuuClasses()) {
					
					if(offerClass.getPK_classKey() == 1) {
						
						if(!resultOffers.contains(offer)) {
							
							resultOffers.add(offer);
						}
						
						break;
					}
	
					int offerFacultyKey = 0;
					
					int offerMajorKey = 0;
					
					int offerClassKey = 0;
					
					if(offerClass.getClassType().equals("2")) {
						
						offerClassKey = offerClass.getPK_classKey();
						
						offerMajorKey = offerClass.getFatherClassKey();
						
						BuuClass offerFaculty = (BuuClass) this.buuClassDao.get(offerClass.getFatherClassKey());
						
						if(offerFaculty != null) {
						
							offerFacultyKey = offerFaculty.getFatherClassKey();
						}
							
					} else if(offerClass.getClassType().equals("1")) {
		
						offerMajorKey = offerClass.getPK_classKey();
						
						offerFacultyKey = offerClass.getFatherClassKey();
					
					} else {
						
						offerFacultyKey = offerClass.getPK_classKey();
					}
					
					if(facultyKey != 0 && facultyKey == offerFacultyKey) {

						if(!resultOffers.contains(offer)) {
						
							resultOffers.add(offer);
						}
						
						break;			
					}
					
					if(majorKey != 0 && majorKey == offerMajorKey) {
						
						if(!resultOffers.contains(offer)) {
							
							resultOffers.add(offer);
						}
						
						break;		
					}
					
					if(classKey != 0 && classKey == offerClassKey) {
						
						if(!resultOffers.contains(offer)) {
							
							resultOffers.add(offer);
						}
						
						break;
					}
				}
			}
		}
		
		return resultOffers;
	}

	@Override
	public List<OfferFlow> getOfferFlowsByOffer(Offer offer) {
		
		return this.offerFlowDao.getOfferFlowsByOfferKey(offer.getPK_offerKey());
	}

	@Override
	public List<Double> offerAnswerAnalyze(int offerFlowKey) {
		
		OfferFlow flow = (OfferFlow) this.offerFlowDao.get(offerFlowKey);
		
		List<OfferAnswer> answers = this.offerAnswerDao.getAnswersInOfferFlow(flow);
		
		int optionNum = this.offerFlowDao.getOptionsNumByOfferFlowKey(offerFlowKey);
		
		List<Double> results = new ArrayList<Double>(optionNum);
		
		for(int i = 0; i < optionNum; i++) {
			
			results.add(0.0);
		}
		
	//	count = new ArrayList<Integer>(optionNum);
		
		if(flow.getFlowType().equals("textInput") || flow.getFlowType().equals("fileUpload")) {
			
			return results;
		}
		
		int nCount = 0;
		
		for(OfferAnswer answer : answers) {
			
			String resultRecords[] = answer.getAnswerRecord().split("[|]");
			
			for(int i = 0; i < resultRecords.length; i++) {
				
				results.set(Integer.parseInt(resultRecords[i]) - 1, results.get(Integer.parseInt(resultRecords[i]) - 1) + 1.0);	
			}
			
		//	nCount += resultRecords.length;
		}
		
		nCount = answers.size();
		
		for(int i = 0; i < results.size(); i++) {
			
			if(nCount == 0) {
				
				results.set(i, 0.0);
				
		//		count.set(i, 0);
			} else {
				
				results.set(i, results.get(i) / nCount);
			}
		//	count.set(i, (int)(double)results.get(i));
		}
		
		return results;
	}

	@Override
	public Offer getOfferByOfferUrl(String url) {
		
		return this.offerDao.getOfferByOfferUrl(url);
	}

	@Override
	public void addOfferAnswersBulk(List<OfferAnswer> offerAnswer) {

		for(int i = 0; i < offerAnswer.size(); i++) {
			
			this.offerAnswerDao.create(offerAnswer.get(i));
		}
	}

	@Override
	public boolean checkStudentAuthentication(String offerUrl, String stuCode) {
		
		Offer offer = this.offerDao.getOfferByOfferUrl(offerUrl);
		
		if(offer == null) {
			
			return false;
		}
		
		Set<BuuClass> classes = offer.getBuuClasses();
		
		Student student = this.studentDao.getStudentByStuCode(stuCode);
		
		if(student == null) {
			
			return false;
		}
		
		for(BuuClass clazz : classes) {
			
			if(clazz.getPK_classKey() == 1) {
				
				return true;
			}
			
			if(student.getClassInfo().getPK_classKey() == clazz.getPK_classKey()) {
				
				return true;
			}
			
			if(student.getMajorInfo().getPK_classKey() == clazz.getPK_classKey()) {
				
				return true;
			}

			if(student.getFacultyInfo().getPK_classKey() == clazz.getPK_classKey()) {
	
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<OfferAnswer> getAnswers(Offer offer) {
		
		List<OfferFlow> offerFlows = this.offerFlowDao.getOfferFlowsByOfferKey(offer.getPK_offerKey());
		
		List<OfferAnswer> answers = new ArrayList<OfferAnswer>();
		
		for(OfferFlow offerFlow : offerFlows) {
			
			List<OfferAnswer> theAnswers = this.offerAnswerDao.getAnswersInOfferFlow(offerFlow);
			
			if(theAnswers != null) {
				
				answers.addAll(theAnswers);
			}
		}
		
		return answers;
	}

	@Override
	public List<Offer> getAllOffers() {
		
		return this.offerDao.getOffers();
	}

	@Override
	public void receiveNewAnswer(int offerKey) {
		
		Offer offer = (Offer) this.offerDao.get(offerKey);
		
		offer.setCountFinish(offer.getCountFinish() + 1);
		
		if(offer.getCountLimit() != 0 && offer.getCountFinish() >= offer.getCountLimit()) {
			
			offer.setOfferStatus(Constraint.OFFER_STATUS_DISABLE);
		}
		
		this.offerDao.update(offer);
	}

	@Override
	public boolean canStudentSubmitOffer(Offer offer, String stuCode) {
		
		if(offer.getOfferStatus().equals(Constraint.OFFER_STATUS_ENABLE) &&
				this.checkStudentAuthentication(offer.getOfferUrl(), stuCode) && 
				(offer.getCountLimit() == 0 || offer.getCountFinish() < offer.getCountLimit()) &&
				!this.hasStudentSubmitted(stuCode, offer.getPK_offerKey())) {
			
			this.receiveNewAnswer(offer.getPK_offerKey());
			
			return true;
		}
		
		return false;
	}

	@Override
	public List<BuuClass> getClassesInOffer(int offerKey) {
		
		Offer offer = (Offer) this.offerDao.get(offerKey);
		
		List<BuuClass> buuclasses = new ArrayList<BuuClass>();
		
		Set<BuuClass> classes = offer.getBuuClasses();
		
		for(BuuClass clazz : classes) {
			
			if(clazz.getPK_classKey() == 1) {
				
				return this.buuClassDao.getAllClasses();
			}
			
			if(clazz.getClassType().equals("0")) {
				
				List<BuuClass> majors = this.buuClassDao.getBuuClassesByClassTypeAndFatherKey("1", clazz.getPK_classKey());
				
				for(BuuClass major : majors) {
					
					List<BuuClass> theClasses = this.buuClassDao.getBuuClassesByClassTypeAndFatherKey("2", major.getPK_classKey());
					
					if(theClasses != null) {
						
						for(BuuClass theClass : theClasses) {
							
							if(!buuclasses.contains(theClass)) {
								
								buuclasses.add(theClass);
							}
						}
					}
				}
			
			} else if(clazz.getClassType().equals("1")) {
				
				List<BuuClass> theClasses = this.buuClassDao.getBuuClassesByClassTypeAndFatherKey("2", clazz.getPK_classKey());
				
				if(theClasses != null) {
					
					for(BuuClass theClass : theClasses) {
						
						if(!buuclasses.contains(theClass)) {
							
							buuclasses.add(theClass);
						}
					}
				}
			
			} else {
						
				if(!buuclasses.contains(clazz)) {
							
					buuclasses.add(clazz);
				}
			}
		}
		
		return buuclasses;
	}

	@Override
	public int getProcessOfClass(BuuClass clazz, int offerKey) {
		
		List<Student> students = this.studentDao.getStudentsByClassInfo(clazz.getPK_classKey());
		
		List<OfferFlow> offerFlows = this.offerFlowDao.getOfferFlowsByOfferKey(offerKey);
		
		if(students == null || students.size() == 0 || offerFlows == null || offerFlows.size() == 0) {
			
			return 0;
		}
		
		int result = 0;
		
		for(Student student : students) {
			
			List<OfferAnswer> answers = this.offerAnswerDao.getAnswerBySubmmiter(student.getStuCode());
			
			boolean isFound = false;
			
			if(answers != null) {
				
				for(OfferAnswer answer : answers) {
					
					if(isFound) {
						
						break;
					}
					
					for(OfferFlow offerFlow : offerFlows) {
						
						if(answer.getBelongedFlow().getPK_flowKey() == offerFlow.getPK_flowKey()) {
							
							result++;
							
							isFound = true;
							
							break;
						}
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public Offer getOfferByKey(int offerKey) {
		
		return (Offer) this.offerDao.get(offerKey);
	}

	@Override
	public String createOfferAnswersExcelFiles(int offerKey) throws Exception {
		
		Offer offer = (Offer) this.offerDao.get(offerKey);
		
		String address = "";
		
		List<String> files = new ArrayList<String>();
		
		List<BuuClass> buuClasses = this.getClassesInOffer(offerKey);
		
		String offerUrl = offer.getOfferUrl();
		
		String[] locations = offerUrl.split("/");
		
		String zipFile = "/" + locations[1] + "/" + locations[2] + "/" + "result.zip";
		
		WritableWorkbook wwb = null;
		
		for(BuuClass clazz : buuClasses) {
		
			address = Constraint.WEB_LOCATION + locations[0] + "/" + locations[1] + "/" + locations[2] + "/" + "resultDetails" + "/";
			
			File excel = new File(address);
			
			if(!excel.exists()) {
				
				excel.mkdirs();
			}
			
			excel = new File(address + clazz.getClassName() + ".xls");
			
			excel.createNewFile();
			
			wwb = Workbook.createWorkbook(excel);
		
			WritableSheet ws = wwb.createSheet("结果", 0);
			
			Label label = new Label(0, 0, "学号");
			
			ws.addCell(label);
			
			label = new Label(1, 0, "姓名");
			
			ws.addCell(label);
			
			label = new Label(2, 0, "联系电话");
			
			ws.addCell(label);
			
			List<OfferFlow> offerFlows = this.offerFlowDao.getOfferFlowsByOfferKey(offer.getPK_offerKey());
			
			int i = 3;
			
			List<OfferFlow> theOfferFlows = new ArrayList<OfferFlow>();
			
			for(OfferFlow offerFlow : offerFlows) {
			
				if(!offerFlow.getFlowSpecies().equals("0")) {
					
					continue;
				}
				
				theOfferFlows.add(offerFlow);
				
				label = new Label(i++, 0, offerFlow.getFlowNum() + "." + offerFlow.getFlowDescription());
				
				ws.addCell(label);
			}
			
			List<Student> students = this.studentDao.getStudentsByClassInfo(clazz.getPK_classKey());
			
			int index = 1;
			
			for(Student student : students) {
				
				label = new Label(0, index, student.getStuCode());
				
				ws.addCell(label);
				
				label = new Label(1, index, student.getName());
				
				ws.addCell(label);
				
				label = new Label(2, index, student.getPhone());
				
				ws.addCell(label);
				
				int optionNum = 3;
				
				for(OfferFlow flow : theOfferFlows) {
					
					OfferAnswer answer = this.offerAnswerDao.getAnswerByOfferFlowAndSubmitter(flow.getPK_flowKey(), student.getStuCode());
					
					String content = "";
					
					if(answer != null) {
					
						if(answer.getBelongedFlow().getFlowType().equals("textInput")) {
					
//							OfferFlow answerFlow = this.offerFlowDao.getOfferFlowByFatherKey(answer.getBelongedFlow().getPK_flowKey());
//							
//							if(answerFlow != null) {
//								
//								content += answerFlow.getFlowDescription();
//							}
							
							content += answer.getAnswerRecord();
							
						} else if(answer.getBelongedFlow().getFlowType().equals("fileUpLoad")) {
							
							content += "请查看附件";
							
						} else {
						
							String[] nums = answer.getAnswerRecord().split("[|]");
					
							for(int k = 0; k < nums.length; k++) {
					
								if(nums[k].equals("") || nums[k].equals("|")) {
								
									continue;
								}
						
								OfferFlow answerFlow = this.offerFlowDao.getOfferFlowByFatherKeyAndFlowNum(answer.getBelongedFlow().getPK_flowKey(), Integer.parseInt(nums[k]));
					
								content += nums[k] + "." + answerFlow.getFlowDescription() + "\n";
							}
						}
					}
					
					label = new Label(optionNum, index, content);
				
					ws.addCell(label);
					
					optionNum++;
				}
			
				index++;
			}
			
			wwb.write();
			
			wwb.close();
			
		//	files.add(address);
		}
		
		files.add(Constraint.WEB_LOCATION + locations[0] + "/" + locations[1] + "/" + locations[2] + "/" + "resultDetails" + "/");
		
		Utility.zipFolder(Constraint.WEB_LOCATION + locations[0] + "/" + locations[1] + "/" + locations[2] + "/" + "resultDetails" + "/", Constraint.WEB_LOCATION + locations[0] + zipFile);
		
		//Utility.zipFiles(files, Constraint.WEB_LOCATION + locations[0] + zipFile);
		
		return zipFile;
	}

	@Override
	public String saveAttachment(File file, String contentType, String stuCode, int offerKey, int flowNum) throws IOException {
		
		Student student = this.studentDao.getStudentByStuCode(stuCode);
		
		BuuClass clazz =student.getClassInfo();
		
		String className = clazz.getClassName();
		
		Offer offer = (Offer) this.offerDao.get(offerKey);
		
		String offerUrl = offer.getOfferUrl();
		
		String[] locations = offerUrl.split("/");
		
		String address = Constraint.WEB_LOCATION + locations[0] + "/" + locations[1] + "/" + locations[2] + "/resultDetails/" + className + "/";
		
		File attachMent = new File(address);
		
		if(!attachMent.exists()) {
			
			attachMent.createNewFile();
		}
		
		address += stuCode + "_" + flowNum + file.getName().substring(file.getName().lastIndexOf("."));
		
		attachMent = new File(address);
		
		attachMent.createNewFile();
		
		Utility.copyFile(file, attachMent);
		
		return attachMent.getName();
	}

	@Override
	public List<Integer> offerAnswerCountAnalyze(int offerFlowKey) {
		
		OfferFlow flow = (OfferFlow) this.offerFlowDao.get(offerFlowKey);
		
		List<OfferAnswer> answers = this.offerAnswerDao.getAnswersInOfferFlow(flow);
		
		int optionNum = this.offerFlowDao.getOptionsNumByOfferFlowKey(offerFlowKey);
		
		List<Integer> count = new ArrayList<Integer>(optionNum);
		
		for(int i = 0; i < optionNum; i++) {
			
			count.add(0);
		}
		
		if(flow.getFlowType().equals("textInput") || flow.getFlowType().equals("fileUpload")) {
			
			return count;
		}
		
		for(OfferAnswer answer : answers) {
			
			String resultRecords[] = answer.getAnswerRecord().split("[|]");
			
			for(int i = 0; i < resultRecords.length; i++) {
				
				count.set(Integer.parseInt(resultRecords[i]) - 1, count.get(Integer.parseInt(resultRecords[i]) - 1) + 1);	
			}
		}
		
		return count;
	}

	@Override
	public boolean hasStudentSubmitted(String stuCode, int offerKey) {
		
		List<OfferFlow> flows = this.offerFlowDao.getOfferFlowsByOfferKey(offerKey);
		
		for(OfferFlow flow : flows) {
			
			OfferAnswer answer = this.offerAnswerDao.getAnswerByOfferFlowAndSubmitter(flow.getPK_flowKey(), stuCode);
			
			if(answer != null) {
				
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<Offer> getAllOffers(int firstResult, int maxResult) {
		
		return this.offerDao.getOffers(firstResult, maxResult);
	}

	@Override
	public boolean canStudentSumbitAttachment(Offer offer, String stuCode) {
		
		if(offer.getOfferStatus().equals(Constraint.OFFER_STATUS_ENABLE) &&
				this.checkStudentAuthentication(offer.getOfferUrl(), stuCode) && 
				(offer.getCountLimit() == 0 || offer.getCountFinish() < offer.getCountLimit()) &&
				!this.hasStudentSubmitted(stuCode, offer.getPK_offerKey())) {
			
			return true;
		}
		
		return false;
	}
}
