import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PsaService {

  constructor(private http: HttpClient, private _router: Router) {
    this.host_url = `${environment.host_url}`
  }
  navigateToDashboard() {
    this._router.navigate(['home/dashboard']);
  }
  host_url: any;
  //project starts
  getAllProject(projectData) {
    return this.http.post(this.host_url + '/obs/projectInfo/get', projectData);
    // return this.http.post('http://localhost:7072/get', projectData);
  }

  getIdProject(id) {
    return this.http.post(this.host_url + '/obs/projectInfo/get', id);
    //return this.http.post('http://localhost:7072/get', id);

  }
  saveProject(data) {
    return this.http.post(this.host_url + '/obs/projectInfo/set', data);
    // return this.http.post("http://localhost:7072/set", data);

  }
  getIdContract(data) {
    return this.http.post(this.host_url + '/obs/customerContract/get', data);


  }
  getProjectContractId(id) {
    return this.http.post(this.host_url + '/obs/projectInfo/get', id);
    //return this.http.post('http://localhost:7072/get', id);


  }


  getBusinessUnit(data) {
    return this.http.post(this.host_url + '/obs/master/businessUnit/get', data);
  }

  getSubBusinessUnit(data) {
    return this.http.post(this.host_url + '/obs/master/subbusinessunit/get ', data);
  }

  // getProjectType(data){
  //    return this.http.post(this.host_url + '/obs/master/projecttype/get ',data);

  // }

  //project ends


  //customer Starts

  getIdCustomer(data) {
    return this.http.post(this.host_url + '/obs/customerinfo/get', data);
  }
  getAllCustomer(data) {
    return this.http.post(this.host_url + '/obs/customerinfo/get', data);
  }
  getallListiofCustomer(requestList) {
    return this.http.post(this.host_url + '/obs/customerinfo/get', requestList);
  }
  getByIDListofCustomer(requestObject) {
    return this.http.post(this.host_url + '/obs/customerinfo/get', requestObject);
  }

  saveCustomerDetails(saveRequest) {
    return this.http.post(this.host_url + '/obs/customerinfo/set', saveRequest);

  }
  updateCustomerDetails(updateRequest) {
    return this.http.post(this.host_url + '/obs/customerinfo/set', updateRequest);
  }
  deleteCustomerDetails(request) {
    return this.http.post(this.host_url + '/obs/customerinfo/set', request);
  }
  // getIdCustomer(data) {
  //   return this.http.post('http://192.168.3.100:7097/obs/customerinfo/get', data);
  // }
  // getAllCustomer(data) {
  //   return this.http.post('http://192.168.3.100:7097/obs/customerinfo/get', data);
  // }
  // getallListiofCustomer(requestList){
  //   return this.http.post("http://192.168.3.100:7097/obs/customerinfo/get",requestList);
  //   }
  //   getByIDListofCustomer(requestObject){
  //   return this.http.post("http://192.168.3.100:7097/obs/customerinfo/get ",requestObject);
  //   }

  //   saveCustomerDetails(saveRequest){
  //     return this.http.post("http://192.168.3.100:7097/obs/customerinfo/set",saveRequest);

  //   }
  //   updateCustomerDetails(updateRequest){
  //     return this.http.post("http://192.168.3.100:7097/obs/customerinfo/set ",updateRequest);
  //   }
  //   deleteCustomerDetails(request){
  //     return this.http.post("http://192.168.3.100:7097/obs/customerinfo/set ",request);
  //   }





  //customer contact info

  saveCustomerContactInfo(reqobj) {
    return this.http.post(this.host_url + '/obs/customerContactInfo/set', reqobj);
  }

  editCustomerContactInfoById(getbyIdReqObj) {
    return this.http.post(this.host_url + '/obs/customerContactInfo/get', getbyIdReqObj);
  }

  updateCustomerContactInfo(updateReqObj) {
    return this.http.post(this.host_url + '/obs/customerContactInfo/set', updateReqObj);
  }

  getCustomerContactInfo(getAllReqObj) {
    return this.http.post(this.host_url + '/obs/customerContactInfo/get', getAllReqObj);
  }

  deleteContactInfo(deleteReqObj) {
    return this.http.post(this.host_url + '/obs/customerContactInfo/set', deleteReqObj);
  }



  // Contract Service

  getAllContractdDetails(reqgetobject) {
    return this.http.post(this.host_url + '/obs/customerContract/get', reqgetobject);

    // return this.http.post("http://192.168.3.162:7098/get", reqgetobject);

  }

  getByIdContractdDetails(reqgetbyidobject) {
    return this.http.post(this.host_url + '/obs/customerContract/get', reqgetbyidobject);
    // return this.http.post("http://192.168.3.162:7098/get", reqgetbyidobject);
  }

  saveContractdDetails(savereqgetobject) {
    return this.http.post(this.host_url + '/obs/customerContract/set', savereqgetobject);

    //return this.http.post("http://192.168.3.162:7098/set", savereqgetobject);
  }

  updateContractdDetails(updatereqgetobject) {
    return this.http.post(this.host_url + '/obs/customerContract/set', updatereqgetobject);

    // return this.http.post("http://localhost:9999/set", updatereqgetobject);

  }

  deleteContractdDetails(deletereqgetobject) {
    return this.http.post(this.host_url + '/obs/customerContract/set', deletereqgetobject);
    // return this.http.post("http://localhost:9999/set", deletereqgetobject);
  }

  getFile(file) {
    return this.http.post(this.host_url + '/obs/customerContract/get', file);

    // return this.http.post("http://localhost:9999/get", file);
  }

  getAllCustomerId(getcustomerreqobj) {
    return this.http.post(this.host_url + '/obs/customerinfo/get', getcustomerreqobj);
    // return this.http.post(this.host_url + '/obs/customerContract/get', getcustomerreqobj);
  }



  //servicetype starts

  setservicetype(servicetypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/servicetype/set', servicetypeReqObj);

  }
  getservicetype(getservicetypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/servicetype/get', getservicetypeReqObj);

  }
  updateservicetype(updateservicetypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/servicetype/set', updateservicetypeReqObj);

  }

  //servicetype ends


  //budgetlist master
  setbudgetlist(setbudgetlistReqObj) {

    return this.http.post(this.host_url + '/obs/master/budget/set', setbudgetlistReqObj);
  }
  getbudgetlist(getsetbudgetlistReqObj) {

    return this.http.post(this.host_url + '/obs/master/budget/get', getsetbudgetlistReqObj);
  }
  updatebudgetlist(updatesetbudgetlistReqObj) {

    return this.http.post(this.host_url + '/obs/master/budget/set', updatesetbudgetlistReqObj);
  }
  //budget list ends 


  //interview mode master
  setInterviewMode(InterviewModeReqObj) {

    return this.http.post(this.host_url + '/obs/master/interviewmode/set', InterviewModeReqObj);

  }
  getInterviewMode(getInterviewModeReqObj) {

    return this.http.post(this.host_url + '/obs/master/interviewmode/get', getInterviewModeReqObj);


  }
  updateInterviewMode(updateInterviewModeReqObj) {

    return this.http.post(this.host_url + '/obs/master/interviewmode/set', updateInterviewModeReqObj);
  }
  //interview mode ends setbudgetlist



  //location type master
  setlocationType(setlocationTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/locationtype/set', setlocationTypeReqObj);
  }
  getlocationType(getlocationTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/locationtype/get', getlocationTypeReqObj);
  }
  updatelocationType(updatelocationTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/locationtype/set', updatelocationTypeReqObj);
  }
  //location type ends

  //Service category master
  setServiceCategory(setServiceCategoryReqObj) {

    return this.http.post(this.host_url + '/obs/master/servicecategory/set', setServiceCategoryReqObj);
  }
  getServiceCategory(getServiceCategoryReqObj) {

    return this.http.post(this.host_url + '/obs/master/servicecategory/get', getServiceCategoryReqObj);
  }
  updateServiceCategory(updateServiceCategoryReqObj) {

    return this.http.post(this.host_url + '/obs/master/servicecategory/set', updateServiceCategoryReqObj);
  }
  //Service category ends


  //delivery location
  setdeliverylocation(setdeliverylocationReqObj) {

    return this.http.post(this.host_url + '/obs/master/deliverylocation/set', setdeliverylocationReqObj);
  }
  getdeliverylocation(getdeliverylocationReqObj) {

    return this.http.post(this.host_url + '/obs/master/deliverylocation/get', getdeliverylocationReqObj);
  }
  updatedeliverylocation(updatedeliverylocationReqObj) {

    return this.http.post(this.host_url + '/obs/master/deliverylocation/set', updatedeliverylocationReqObj);
  }
  getDelivertLocationByidState(getByid) {
    return this.http.post(this.host_url + '/obs/master/deliverylocation/get', getByid);
  }
  //delivery location ends

  //Billing Type
  setbillingtype(setbillingtypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/billingtype/set', setbillingtypeReqObj);
  }
  getbillingtype(getbillingtypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/billingtype/get', getbillingtypeReqObj);
  }
  updatebillingtype(updatebillingtypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/billingtype/set', updatebillingtypeReqObj);
  }
  //Billing Type ends 

  //Rate type
  setRatetype(setRatetypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/ratetype/set ', setRatetypeReqObj);
  }
  getRatetype(getRatetypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/ratetype/get ', getRatetypeReqObj);
  }
  updateRatetype(updateRatetypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/ratetype/set', updateRatetypeReqObj);
  }
  //Rate type ends

  //C2H type
  setC2H(setC2HReqObj) {

    return this.http.post(this.host_url + '/obs/master/c2hstatus/set', setC2HReqObj);
  }
  getC2H(getC2HReqObj) {

    return this.http.post(this.host_url + '/obs/master/c2hstatus/get', getC2HReqObj);
  }
  updateC2H(updateC2HReqObj) {

    return this.http.post(this.host_url + '/obs/master/c2hstatus/set', updateC2HReqObj);
  }
  //C2H ends
  //sez location

  setSezLoc(setSezLocReqObj) {

    return this.http.post(this.host_url + '/obs/master/sezlocation/set', setSezLocReqObj);
  }
  getSezLoc(getSezLocReqObj) {

    return this.http.post(this.host_url + '/obs/master/sezlocation/get', getSezLocReqObj);
  }
  updateSezLoc(updateSezLocReqObj) {

    return this.http.post(this.host_url + '/obs/master/sezlocation/set', updateSezLocReqObj);
  }

  //sez location ends

  setProjectTechStack(setProjectReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttechstack/set', setProjectReqObj);

  }

  getProjectTechStack(getProjectReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttechstack/get', getProjectReqObj);
  }

  UpdateProjectTechstack(UpdateProjectReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttechstack/set', UpdateProjectReqObj);
  }




  getactionOwner(getActionOwnerReqObj) {
    return this.http.post(this.host_url + '/obs/master/actionowner/get ', getActionOwnerReqObj);
  }
  setActionOwner(setActionOwnerReqObj) {
    return this.http.post(this.host_url + '/obs/master/actionowner/set ', setActionOwnerReqObj);
  }
  updateactionowner(updateactionownerReqobj) {
    return this.http.post(this.host_url + '/obs/master/actionowner/set ', updateactionownerReqobj);
  }
  //action owner ends

  //sarstatus starts

  setsarstaustype(sarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/sarstatus/set', sarstatustypeReqObj);

  }
  getsarstaustype(getsarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/sarstatus/get', getsarstatustypeReqObj);

  }
  updatesarstatustype(updatesarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/sarstatus/set', updatesarstatustypeReqObj);

  }

  //sarstatus ends

  //InterviewResults starts

  setInterviewResults(sarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/interviewresult/set', sarstatustypeReqObj);

  }
  getInterviewResults(getsarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/interviewresult/get', getsarstatustypeReqObj);

  }
  updatInterviewResults(updatesarstatustypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/interviewresult/set', updatesarstatustypeReqObj);

  }

  //InterviewResults ends
  //permstatus
  savepermstatus(setpermstatusobj) {
    return this.http.post(this.host_url + '/obs/master/permstatus/set', setpermstatusobj);
  }
  getpermstatus(getpermstatusobj) {
    return this.http.post(this.host_url + '/obs/master/permstatus/get', getpermstatusobj);
  }
  updatepermstatus(updatepermstatusobj) {
    return this.http.post(this.host_url + '/obs/master/permstatus/set', updatepermstatusobj);
  }

  //permstatus ends


  //Action Type

  setActionType(setActionTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/actiontype/set', setActionTypeReqObj);

  }

  getActionType(getActionTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/actiontype/get', getActionTypeReqObj);
  }

  updateActionType(updateActionReqObj) {

    return this.http.post(this.host_url + '/obs/master/actiontype/set', updateActionReqObj);
  }

  //end

  // Project Task

  setProjectTask(setProjectTaskReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttask/set', setProjectTaskReqObj);
  }

  getProjectTask(getProjectTaskReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttask/get ', getProjectTaskReqObj);
  }

  updateProjectTask(updateProjectTaskReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttask/set', updateProjectTaskReqObj);
  }

  //end
  //GST Location starts

  setGstLocation(GstLocationReqObj) {
    return this.http.post(this.host_url + '/obs/master/gstlocation/set', GstLocationReqObj);

  }
  getGstLocation(GstLocationReqObj) {
    return this.http.post(this.host_url + '/obs/master/gstlocation/get', GstLocationReqObj);

  }
  updateGstLocation(GstLocationReqObj) {
    return this.http.post(this.host_url + '/obs/master/gstlocation/set', GstLocationReqObj);

  }

  //GST Location ends
  // Document-Type starts

  setDocument_Type(Document_TypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/documenttype/set', Document_TypeReqObj);

  }
  getDocument_Type(Document_TypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/documenttype/get', Document_TypeReqObj);

  }
  updateDocument_Type(Document_TypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/documenttype/set', Document_TypeReqObj);

  }

  //Document-Type ends

  //document stage
  getDocument(getDocumentReqObj) {
    return this.http.post(this.host_url + '/obs/master/documentstage/get', getDocumentReqObj);
  }
  setDocument(setDocumentReqObj) {
    return this.http.post(this.host_url + '/obs/master/documentstage/set', setDocumentReqObj);
  }
  UpdateDocument(UpdateDocumentReqObj) {
    return this.http.post(this.host_url + '/obs/master/documentstage/set', UpdateDocumentReqObj);
  }
  //document stage ends



  //contract company
  savecomcon(setcomcontobj) {
    return this.http.post(this.host_url + '/obs/master/contractcompany/set', setcomcontobj);
  }
  getcomcon(getcomcontobj) {
    return this.http.post(this.host_url + '/obs/master/contractcompany/get', getcomcontobj);
  }
  updatecomcon(updatecomcontobj) {
    return this.http.post(this.host_url + '/obs/obs/master/contractcompany/set', updatecomcontobj);
  }
  getcomconbyid(updatecomcontobj) {
    return this.http.post(this.host_url + '/obs/master/contractcompany/get', updatecomcontobj);
  }
  deletecomcon(deletecomconobj) {
    return this.http.post(this.host_url + '/obs/master/contractcompany/set', deletecomconobj);
  }
  //contract company ends
  //geo location
  SetGeo(SetGeoReqObj) {
    return this.http.post(this.host_url + '/obs/master/geo/set', SetGeoReqObj);
  }

  GetGeo(GetGeoReqObj) {
    return this.http.post(this.host_url + '/obs/master/geo/get', GetGeoReqObj);
  }
  UpdateGeo(UpdateGeoReqObj) {
    return this.http.post(this.host_url + '/obs/master/geo/set', UpdateGeoReqObj);
  }
  //geo loc ends

  //country

  SetCountry(SetCountryReqObj) {
    return this.http.post(this.host_url + '/obs/master/country/set', SetCountryReqObj);
  }

  GetCountry(GetCountryReqObj) {
    return this.http.post(this.host_url + '/obs/master/country/get', GetCountryReqObj);
  }

  UpdateCountry(UpdateCountryReqObj) {
    return this.http.post(this.host_url + '/obs/master/country/set', UpdateCountryReqObj);
  }
  getcontrybygeoid(obj) {
    return this.http.post(this.host_url + '/obs/master/country/get', obj);
  }
  //country ends
  //projectype//
  getProjectType(getProjectTypeReqObj) {

    return this.http.post(this.host_url + '/obs/master/projecttype/get', getProjectTypeReqObj);

  }
  setProjectType(setProjectTypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttype/set', setProjectTypeReqObj);
  }
  updateProjectType(UpdateProjectTypeReqObj) {
    return this.http.post(this.host_url + '/obs/master/projecttype/set', UpdateProjectTypeReqObj);
  }


  //billing resources

  getData(obj) {
    return this.http.post(this.host_url + '/obs/rmg/get', obj)
  }

  getSkillBased(obj) {
    return this.http.post(this.host_url + "/obs/employeeInfo/get", obj)
  }
  // psa dashboard apis
  getEmployeeSkill(req) {
    return this.http.post(this.host_url + '/obs/rmg/get', req)

  }
}
