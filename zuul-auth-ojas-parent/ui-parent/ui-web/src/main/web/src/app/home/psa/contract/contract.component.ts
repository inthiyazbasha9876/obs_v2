import { FilterPipe } from './../../pipes/filter.pipe';
import { Component, OnInit, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';

// import { ToastrManager } from 'ng6-toastr-notifications';
import swal from 'src/assets/sweetalert';
import { resolve } from 'q';
import { Subscription, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { HrmsService } from '../../services/hrms.service';
import { AuthService } from 'src/app/services/auth.service';
import { PsaService } from '../../services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

declare var $;

@Component({
  selector: 'app-contract',
  templateUrl: './contract.component.html',
  styleUrls: ['./contract.component.scss']
})
export class ContractComponent implements OnInit {
  @ViewChild('someModal', { static: false }) someModal: ElementRef;

  contractSubscription: Subject<any> = new Subject();

  hide = false;
  submitted = false;
  getAllcontractdata: any;
  contractAllDetails: any;
  savecontractdata: any;
  updatecontractdata: any;
  getbyidcontractdata: any;
  getbyidcontractres: any;
  getcontractId: any;
  updatecontractId: any;
  deletecontractId: any;
  deleteidcontractres: any;
  deleteidcontractdata: any;
  data: any[];
  customersdata: any;
  customersAllData: any;
  customerId: any
  createBtn: any = true
  servicetypelist: any;
  serviceTypeList: any;
  contractForm: FormGroup;
  pageSize = 5;
  deliveryLoclist: any;
  deleiveryData: any;
  cf: any;
  pick = true;
  files: any;
  filestring: string;
  status: any = false
  documenttype: any;
  documentstage: any;
  documenttypedata: any;
  contractcompanylist: any;
  documenttypelist1: any;
  contractcompanydata: any;
  cid: any;
  moreinfo = false;
  customerName: string;
  bdmconatctName: string;
  ServiceName: any[];
  customerData: any;
  response: any;
  loggedUserName: any;
  comment: string = "Contract is pending, waiting for approval from BU Head and Finance!";
  approveResopnse: any;
  approveMessage: any;
  msg: any;
  role: boolean;
  roleEdit: boolean;
  roleStatusFinance: boolean;
  roleStatusBU: boolean;
  empbasic: any;
  empbasicin: any;
  business: any;
  businessUnit: any;
  buListArr: any = [];
  sbuListArr: any = [];
  subBusiness: any;
  subBusinessUnit: any;
  documentdata: any;
  docdata: any;
  conid: any;
  encryptedUsername: string;
  encryptedRole: string;
  role1: any;
  buFullName: any;
  sbuFullName: any;
  custName: any;


  constructor(private formBuilder: FormBuilder, private psa: PsaService, private hrms: HrmsService, private authService: AuthService, private toast: NotificationService) {
    // , private toster: ToastrManager


    // this.loggedUserName = sessionStorage.getItem("UserName");

    this.encryptedUsername = sessionStorage.getItem('UserName');
    this.loggedUserName = this.authService.decryptData(this.encryptedUsername);
    this.encryptedRole = sessionStorage.getItem("Role");
    this.role1 = this.authService.decryptData(this.encryptedRole);

    this.contractForm = this.formBuilder.group({
      selectCustomer: [''],
      contractName: ['', [Validators.required, Validators.pattern("[A-Za-z ]*")]],
      contractStartDate: ['', [Validators.required]],
      contractEndDate: ['', [Validators.required]],
      contractOwner: ['', [Validators.required, Validators.pattern("[A-Za-z ]*")]],
      contractValue: ['', [Validators.required, Validators.pattern("[0-9]*(\.[0-9]{0,2})?$")]],
      serviceType: ['', [Validators.required]],
      deliveryLocation: ['', [Validators.required]],
      contractCurrency: ['',],
      executingCompany: ['', [Validators.required]],
      contractCompany: ['', [Validators.required]],
      documentType: ['', [Validators.required]],
      documentStage: ['', [Validators.required]],
      multilocationDelivery: ['', [Validators.required]],
      documentName: ['', [Validators.required]],
      buHead: ['', [Validators.required]],
      sbuHead: ['', [Validators.required]],
      contractType: ['', [Validators.required]],
      contractDescription: [''],


    });

  }

  ngOnInit() {

    this.getContractAll();
    this.getAllCustomerData();
    this.getServiceTypeInfo();
    this.getdeliveryLocation();
    this.getDocument();
    this.getdocumenttype();
    this.getcomcontract();
    this.getBusinessUnit();
    this.getAllEmployees();
    this.getSubBusinessUnit();



    if (this.role1 == "ROLE_BDM" || this.role1 == "ROLE_BDMHEAD" || this.role1 == "ROLE_ADMIN" || this.role1 == "ROLE_STAFFAUGHEAD" || this.role1 == "ROLE_SALES") {
      this.role = true;
    }
    if (this.role1 == "ROLE_BDM" || this.role1 == "ROLE_BDMHEAD" || this.role1 == "ROLE_ADMIN" || this.role1 == "ROLE_STAFFAUGHEAD" || this.role1 == "ROLE_SALES" || this.role1 == "ROLE_SBUHEAD") {
      this.roleEdit = true;
    }



  }

  ngOnDestroy(): any {
    this.contractSubscription.next();
    this.contractSubscription.complete();
  }



  customerid(customerid: any) {
    throw new Error("Method not implemented.");
  }
  private compareSelectValues(selectedValue, compareValue): boolean {
    return Number(selectedValue) === compareValue;
  }

  hidefunc() {
    this.status = true;
  }

  change(data){
console.log(this.customersAllData,"datapbject");

var dtaa= this.customersAllData.find(s=>s.customerId==data)
return dtaa.customerName



  }


  getContractAll() {
    this.data = []
    var contractdetails =
    {
      "customercontractdetailslist": [{

      }],
      "transactiontype": "getall"
    }

    this.psa.getAllContractdDetails(contractdetails).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.getAllcontractdata = res;
      this.contractAllDetails = this.getAllcontractdata.customercontractdetailslist;
      console.log("contracts", this.contractAllDetails)

      for (let i in this.contractAllDetails) {
        // if ((this.customersAllData != null) ||(this.customersAllData!=undefined)) {
          // console.log('enters');
          
          // let custData = this.customersAllData.find(type => type.customerId == this.contractAllDetails[i].customerid)
          // this.contractAllDetails[i].customerid = custData.customerName;
          // console.log(this.contractAllDetails[i].customerId,"customerName");
          
        // }
        // else{
        //   console.log('exits');
          
        //  this.getContractAll();
          
        // }

        // console.log("deatils", this.contractAllDetails[i].customerid);
      }

      for (let i in this.contractAllDetails) {
        if (this.contractAllDetails[i].status == true) {
          this.data.push(this.contractAllDetails[i])
        }
      }



    })
  }


  fileSelected(event) {
    this.files = event.target.files;
    var reader = new FileReader();
    reader.onload = this.handleReaderLoaded.bind(this);
    reader.readAsBinaryString(this.files[0]);
  }
  handleReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.filestring = btoa(binaryString);  // Converting binary string data.
    console.log("Contract File Data : ", this.filestring);

  }

  saveContract() {
    var date = new Date();

    var savereq =
    {
      "customercontractdetailslist": [{
        "customerid": this.contractForm.value.selectCustomer,
        "contractname": this.contractForm.value.contractName,
        "description": this.contractForm.value.contractDescription,
        "startdate": this.contractForm.value.contractStartDate,
        "enddate": this.contractForm.value.contractEndDate,
        "servicetype": this.contractForm.value.serviceType,
        "deliverylocation": this.contractForm.value.deliveryLocation,
        "createdby": this.loggedUserName,
        "contractvalue": this.contractForm.value.contractValue,
        "contractowner": this.contractForm.value.contractOwner,
        "status": true,
        "contractcurrency": this.contractForm.value.contractCurrency,
        "executingcompany": this.contractForm.value.executingCompany,
        "contractcompany": this.contractForm.value.contractCompany,
        "documenttype": this.contractForm.value.documentType,
        "documentstage": this.contractForm.value.documentStage,
        "multilocationdelivery": this.contractForm.value.multilocationDelivery,
        "document": this.filestring,
        "buStatus": "Pending",
        "financeStatus": "Pending",
        "updatedBy": this.loggedUserName,
        "createdDate": date,
        "updatedDate": date,
        "buHead": this.contractForm.value.buHead,
        "sbuHead": this.contractForm.value.sbuHead,
        "contractType": this.contractForm.value.contractType,
        "comment": this.comment

      }],
      "transactiontype": "save"
    }
    let msg: any;
    console.log("save request contract obj", savereq);
    this.psa.saveContractdDetails(savereq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      $(this.someModal.nativeElement).modal('hide');
      this.savecontractdata = res;
      msg = this.savecontractdata;
      //console.log("msg",msg);

      // this.toster.successToastr(msg.message, 'success', {
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.success(msg.message, "Success")


      // swal(this.savecontractdata.message, "", "success");
      // this.getByIdContract(this.getcontractId, this.cid);
      this.getContractAll();
    },
      err => {
        console.log(err)
        msg = err
        // this.toster.infoToastr(msg.error.error, 'info', {
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.info(msg.error.error, 'Info')
      });
  }


  updateContract(id, formdata, custid) {

    console.log(id);
    console.log(formdata);
    var date = new Date();

    var updatereq =
    {
      "customercontractdetailslist": [{
        "customerid": custid,
        "contractid": id,
        "contractname": formdata.contractName,
        "description": formdata.contractDescription,
        "startdate": formdata.contractStartDate,
        "enddate": formdata.contractEndDate,
        "servicetype": formdata.serviceType,
        "deliverylocation": formdata.deliveryLocation,
        "createdby": this.loggedUserName,
        "contractvalue": formdata.contractValue,
        "contractowner": formdata.contractOwner,
        "status": true,
        "contractcurrency": formdata.contractCurrency,
        "executingcompany": formdata.executingCompany,
        "contractcompany": formdata.contractCompany,
        "documenttype": formdata.documentType,
        "documentstage": formdata.documentStage,
        "multilocationdelivery": formdata.multilocationDelivery,
        "document": this.filestring,
        "buStatus": "Pending",
        "financeStatus": "Pending",
        "updatedBy": this.loggedUserName,
        "createdDate": date,
        "updatedDate": date,
        "buHead": formdata.buHead,
        "sbuHead": formdata.sbuHead,
        "contractType": formdata.contractType,
        "comment": this.comment
      }],
      "transactiontype": "update"
    }
    let msg: any;
    console.log("update request contract obj", updatereq);

    this.psa.updateContractdDetails(updatereq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {

      this.updatecontractdata = res;
      msg = this.updatecontractdata;
      $(this.someModal.nativeElement).modal('hide');
      // this.toster.successToastr(msg.message, 'success', {
      //   showCloseButton: true,
      //   animate: 'slideFromRight'
      // })
      this.toast.success(msg.message, "Success")
      // swal(this.updatecontractdata.message, "", "success");
      this.getByIdContract(this.getcontractId, this.cid);
      this.getContractAll();
    },
      err => {
        console.log(err)
        msg = err
        // this.toster.infoToastr(msg.error.error, 'info', {
        //   showCloseButton: true,
        //   animate: 'slideFromRight'
        // })
        this.toast.info(msg.error.error, 'Info')
      });

  }


  getByIdContract(getcontid, e) {
    // console.log("get customer from html", e)
    this.getcontractId = getcontid;
    this.cid = e
    // console.log("id value", this.getcontractId);

    var getbyidreq = {
      "customercontractdetailslist": [{

        "contractid": this.getcontractId

      }],
      "transactiontype": "getbyid"
    }
    this.moreinfo = true;

    this.psa.getByIdContractdDetails(getbyidreq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.getbyidcontractres = res;
      this.getbyidcontractdata = this.getbyidcontractres.customercontractdetailslist;
      console.log("Get by ContractId data", this.getbyidcontractdata);


      let custObj = this.customersAllData.find(type => type.customerId == this.getbyidcontractdata[0].customerid)
      this.custName = custObj.customerName;
      console.log('custObj', this.custName);

      let empObj = this.empbasicin.find(name => name.employeeId == this.getbyidcontractdata[0].buHead)
      console.log('buid', empObj);
      if (empObj.middlename == null || empObj.middlename == '') {
        this.buFullName = empObj.firstname + ' ' + empObj.lastname;
      } else {
        this.buFullName = empObj.firstname + ' ' + empObj.middlename + ' ' + empObj.lastname;
      }

      let empObj1 = this.empbasicin.find(name => name.employeeId == this.getbyidcontractdata[0].sbuHead)
      console.log('sbuid', empObj1);
      if (empObj1.middlename == null || empObj1.middlename == '') {
        this.sbuFullName = empObj1.firstname + ' ' + empObj1.lastname;
      } else {
        this.sbuFullName = empObj1.firstname + ' ' + empObj1.middlename + ' ' + empObj1.lastname;
      }


      if (this.getbyidcontractdata[0].buStatus == "Approved" && this.getbyidcontractdata[0].financeStatus == "Pending" && this.role1 == "ROLE_FINANCE") {
        this.roleStatusFinance = true;
      }
      if (this.getbyidcontractdata[0].buStatus == "Pending" && this.getbyidcontractdata[0].financeStatus == "Pending" && this.role1 == "ROLE_BUHEAD") {
        this.roleStatusBU = true;
      }

      if (this.getbyidcontractdata[0].buStatus == "Approved" || this.getbyidcontractdata[0].buStatus == "Rejected") {
        this.roleStatusBU = false;
      }
      if (this.getbyidcontractdata[0].financeStatus == "Approved" || this.getbyidcontractdata[0].financeStatus == "Rejected") {
        this.roleStatusFinance = false
      }

    })
  }


  back() {
    this.roleStatusFinance = false;
    this.getContractAll();
  }


  edit() {
    this.contractForm.controls.contractName.setValue(this.getbyidcontractdata[0].contractname)
    this.contractForm.controls.contractStartDate.setValue(this.formatDate(new Date(this.getbyidcontractdata[0].startdate)))
    this.contractForm.controls.contractEndDate.setValue(this.formatDate(new Date(this.getbyidcontractdata[0].enddate)))
    this.contractForm.controls.contractOwner.setValue(this.getbyidcontractdata[0].contractowner)
    this.contractForm.controls.contractOwner.setValue(this.getbyidcontractdata[0].contractowner)
    this.contractForm.controls.contractValue.setValue(this.getbyidcontractdata[0].contractvalue)
    this.contractForm.controls.serviceType.setValue(this.getbyidcontractdata[0].servicetype)
    this.contractForm.controls.deliveryLocation.setValue(this.getbyidcontractdata[0].deliverylocation)
    this.contractForm.controls.contractDescription.setValue(this.getbyidcontractdata[0].description)
    this.contractForm.controls.contractCurrency.setValue(this.getbyidcontractdata[0].contractcurrency)
    this.contractForm.controls.executingCompany.setValue(this.getbyidcontractdata[0].executingcompany)
    this.contractForm.controls.contractCompany.setValue(this.getbyidcontractdata[0].contractcompany)
    this.contractForm.controls.documentType.setValue(this.getbyidcontractdata[0].documenttype)
    this.contractForm.controls.documentStage.setValue(this.getbyidcontractdata[0].documentstage)
    this.contractForm.controls.multilocationDelivery.setValue(this.getbyidcontractdata[0].multilocationdelivery)
    this.contractForm.controls.documentName.setValue(this.getbyidcontractdata[0].document)
    this.contractForm.controls.buHead.setValue(this.getbyidcontractdata[0].buHead)
    this.contractForm.controls.sbuHead.setValue(this.getbyidcontractdata[0].sbuHead)
    this.contractForm.controls.contractType.setValue(this.getbyidcontractdata[0].contractType)


  }


  getFileData(coid) {
    console.log('doc value id', coid);
    var filedata = {
      "customercontractdetailslist": [
        {

          "contractid": coid

        }],
      "transactiontype": "getfile"
    }
    this.psa.getFile(filedata).subscribe(res => {
      this.documentdata = res;
      this.docdata = this.documentdata.document;
      console.log("File data ", this.docdata);
      this.downloadFile()
    })
  }


  downloadFile() {


    let pdfdata = 'data:application/pdf;base64,' + this.docdata;
    // console.log("File Data ", pdfdata);
    let downloadLink = document.createElement('a');
    downloadLink.href = pdfdata;
    downloadLink.download = 'Contract Documents '; // here file name
    downloadLink.click();

  }


  deleteContract(deleteconid) {

    console.log("id value", deleteconid);

    var deletereq = {
      "customercontractdetailslist": [
        {

          "contractid": deleteconid

        }],
      "transactiontype": "delete"
    }
    let msg;

    swal({
      title: "Are you sure?",
      text: "Once deleted, you will not be able to recover this activity!",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {

          this.psa.deleteContractdDetails(deletereq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
            this.deleteidcontractdata = res;
            msg = this.deleteidcontractdata

            // this.toster.successToastr(msg.message, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(msg.message, "Success")

            console.log("delete request contract obj", this.deleteidcontractdata);
            // swal(this.deleteidcontractdata.message, "", "success");
            this.getContractAll();
          }
            , err => {
              console.log(err)
              msg = err
              // this.toster.infoToastr(msg.error.error, 'info', {
              //   showCloseButton: true,
              //   animate: 'slideFromRight'
              // })
              this.toast.info(msg.error.error, 'Info')
            });

        }
        else {
          // this.toster.errorToastr('You can not delete saved data', 'Error', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.error('You can not delete saved data', "Error")
        }
      });

  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }


  // Customer Details
  getAllCustomerData() {

    var getcustomerreq = {
      "customerList":
      {

      },
      "transactionType": "getall"
    }

    this.psa.getallListiofCustomer(getcustomerreq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.customersdata = res;
      this.customersAllData = this.customersdata.customerList;
      console.log("customers data", this.customersdata);

    })

  }

  selectedItem(e) {
    this.customerId = e

    // this.createBtn = false
    this.status = true
    console.log("selected customer", this.customerId)
    this.getCustomerDataById(this.customerId);


  }
  // getCustomerDataById(customerId: any) {
  //   throw new Error("Method not implemented.");
  // }


  getCustomerDataById(customerid) {

    var request =
    {
      "customerList":
      {
        "customerId": customerid

      },



      "transactionType": "getbyid"
    }
    console.log("customer request", request);

    this.psa.getIdCustomer(request).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      console.log(res, "getByidcustomer")
      this.response = res;
      console.log(this.response.customerList, "response mesage");
      console.log(this.response.customerList[0].customerName, "response mesage");
      console.log(this.response.customerList[0].billinginfo.bdmconatctName, "bdm name");

      this.customerName = this.response.customerList[0].customerName;
      console.log('customer name', this.customerName);
      this.servicetypelist = this.response.customerList[0].servicetype;
      this.customerData = this.response.customerList[0];
      this.bdmconatctName = this.response.customerList[0].billinginfo.bdmconatctName;

      //  var qua = this.customerList.find(q => q.id == this.table_data[i].qualification)
      //         this.table_data[i].qualification=qua.educationType

    })
  }


  clear() {

    this.customerId = "";
    this.customerName = "";
    this.bdmconatctName = "";
    this.ServiceName = [''];
    this.contractvalue = "";

    this.status = false


  }

  //end

  // Servicetype master 
  //  getall

  getServiceTypeInfo() {

    var Requestdata = {
      "servicetypeList": [{

      }],
      "transactionType": "getall"
    }
    this.psa.getservicetype(Requestdata).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.servicetypelist = res;
      this.serviceTypeList = this.servicetypelist.servicetypeList;
      console.log("Get servicetype data", this.serviceTypeList)
      // this.serviceTypeAssign=this.servicetypelist.servicetypeList;
    })
  }


  // end


  // Delivery Location master
  //getall
  getdeliveryLocation() {

    var Requestdata = {
      "deliverylocationList": [{

      }],
      "transactionType": "getall"
    }

    this.psa.getdeliverylocation(Requestdata).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.deliveryLoclist = res;
      this.deleiveryData = this.deliveryLoclist.deliverylocationList;
      console.log("Get deleiverylocation data", this.deleiveryData)

    })

  }
  //end

  // Document Stage Master

  getDocument() {
    console.log("getDocument called");

    var Requestdata = {
      "doucumentStageList": [{

      }],
      "transactionType": "getall"
    }

    this.psa.getDocument(Requestdata).pipe(takeUntil(this.contractSubscription)).subscribe(responce => {
      this.documenttype = responce;
      this.documentstage = this.documenttype.doucumentStageList;
      console.log("Get Document Statge Data", this.documentstage)
    })
  }
  //end

  // Document Type master

  getdocumenttype() {

    var Requestdata = {

      "documenttypelist": [{

      }],
      "transactionType": "getall"
    }

    this.psa.getDocument_Type(Requestdata).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.documenttypelist1 = res;
      this.documenttypedata = this.documenttypelist1.documenttypelist;
      console.log("Get Document Type Data", this.documenttypedata)
    })

  }
  //end

  // Contract company master
  getcomcontract() {

    var comconreq = {
      "companyList": [{

      }],
      "transactionType": "getall"
    }
    this.psa.getcomcon(comconreq).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.contractcompanydata = res;
      this.contractcompanylist = this.contractcompanydata.comapnayList;
      console.log("Contract company data", this.contractcompanylist);

    })

  }
  //end




  //aprove BU Head
  approveBU() {
    var date = new Date();
    var data = {
      "customercontractdetailslist": [
        {

          "contractid": this.getbyidcontractdata[0].contractid,

          "updatedBy": this.loggedUserName,
          "buStatus": "Approved",
          "buApprovedDate": date,
          "financeStatus": "Pending",
          "comment": "Contract approved by the BU and forwarded to finance"
        }],
      "transactiontype": "statusupdate"
    }



    swal({
      title: "Are you sure?",
      text: "Want to approve the Contract",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psa.saveContractdDetails(data).pipe(takeUntil(this.contractSubscription)).subscribe(response => {
            console.log("approve", response);
            this.approveResopnse = response;
            this.approveMessage = this.approveResopnse.message;

            // this.toster.successToastr(this.approveMessage, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.approveMessage, "Success")
            this.getByIdContract(this.getbyidcontractdata[0].contractid, this.getbyidcontractdata[0].customerid);


          }, err => {
            console.log(err)
            this.msg = err
            // this.toster.infoToastr(this.msg.error.message, 'info', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, "Info")
          });

        }
        else {
          // this.toster.errorToastr('You did not approve project', 'Error', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.error("You did not approve project", "Error")
        }
      });

  }


  //reject

  rejectBU(cmnt) {
    var data = {
      "customercontractdetailslist": [
        {

          "contractid": this.getbyidcontractdata[0].contractid,
          "buStatus": "Rejected",
          "financeStatus": "Pending",
          "updatedBy": this.loggedUserName,
          "comment": cmnt.comments

        }],
      "transactiontype": "statusupdate"
    }
    console.log("res", data);

    swal({
      title: "Are you sure?",
      text: "Want to reject the Contract",

      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psa.saveContractdDetails(data).pipe(takeUntil(this.contractSubscription)).subscribe(response => {
            console.log("reject", response);
            this.approveResopnse = response;
            this.approveMessage = this.approveResopnse.message;

            // this.toster.successToastr(this.approveMessage, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.approveMessage, "Success")
            this.getByIdContract(this.getbyidcontractdata[0].contractid, this.getbyidcontractdata[0].customerid);

          }, err => {
            console.log(err)
            this.msg = err
            // this.toster.infoToastr(this.msg.error.message, 'error', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, "Error")
          });

        }
        else {
          // this.toster.errorToastr('You did not reject project', 'info', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.error("You did not reject project", "Info")
        }
      });
  }


  // approve Finance
  approveFinance() {
    var data = {
      "customercontractdetailslist": [
        {

          "contractid": this.getbyidcontractdata[0].contractid,

          "updatedBy": this.loggedUserName,
          "buStatus": "Approved",
          "financeStatus": "Approved",
          "comment": "Contract have been approved"
        }],
      "transactiontype": "statusupdate"
    }



    swal({
      title: "Are you sure?",
      text: "Want to approve the Contract",
      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psa.saveContractdDetails(data).pipe(takeUntil(this.contractSubscription)).subscribe(response => {
            console.log("approve", response);
            this.approveResopnse = response;
            this.approveMessage = this.approveResopnse.message;

            // this.toster.successToastr(this.approveMessage, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.approveMessage, "Success")
            this.getByIdContract(this.getbyidcontractdata[0].contractid, this.getbyidcontractdata[0].customerid);


          }, err => {
            console.log(err)
            this.msg = err
            // this.toster.infoToastr(this.msg.error.message, 'info', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, "Info")
          });

        }
        else {
          // this.toster.errorToastr('You did not approve project', 'Error', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.error('You did not approve project', "Error")
        }
      });

  }


  //reject Finance

  rejectFinance(cmnt) {
    var data = {
      "customercontractdetailslist": [
        {

          "contractid": this.getbyidcontractdata[0].contractid,
          "buStatus": "Approved",
          "financeStatus": "Rejected",
          "updatedBy": this.loggedUserName,
          "comment": cmnt.comments

        }],
      "transactiontype": "statusupdate"
    }
    console.log("res", data);

    swal({
      title: "Are you sure?",
      text: "Want to reject the Contract",

      buttons: [
        'No, cancel it!',
        'Yes, I am sure!'
      ],
      dangerMode: true,
    })
      .then((proceed) => {
        if (proceed) {

          this.psa.saveContractdDetails(data).pipe(takeUntil(this.contractSubscription)).subscribe(response => {
            console.log("reject", response);
            this.approveResopnse = response;
            this.approveMessage = this.approveResopnse.message;

            // this.toster.successToastr(this.approveMessage, 'success', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.success(this.approveMessage, "Success")
            this.getByIdContract(this.getbyidcontractdata[0].contractid, this.getbyidcontractdata[0].customerid);

          }, err => {
            console.log(err)
            this.msg = err
            // this.toster.infoToastr(this.msg.error.message, 'error', {
            //   showCloseButton: true,
            //   animate: 'slideFromRight'
            // })
            this.toast.info(this.msg.error.message, "Error")
          });

        }
        else {
          // this.toster.errorToastr('You did not reject project', 'info', {
          //   animate: 'slideFromRight',
          //   showCloseButton: true,
          // });
          this.toast.error("You did not reject project", "Info")
        }
      });
  }



  //all employees


  getAllEmployees() {
    var empinfo =
    {
      "employeeInfo": [{
      }],
      "transactionType": "getall"
    }

    this.hrms.getempinfo(empinfo).pipe(takeUntil(this.contractSubscription)).subscribe(res => {
      this.empbasic = res;
      this.empbasicin = this.empbasic.employeeInfo;
      console.log("All Employees", this.empbasicin);
      console.log("Business Unit", this.businessUnit);
      for (var i = 0; i < this.businessUnit.length; i++) {
        let buObj = {
          'buHead': "",
          "buHeadName": "",
          "buName": ""
        }
        let empObj = this.empbasicin.find(type => type.employeeId == this.businessUnit[i].buHead)
        console.log("Find employee : ", empObj);
        buObj.buHeadName = empObj.firstname + empObj.lastname;
        buObj.buHead = empObj.employeeId;
        buObj.buName = this.businessUnit[i].businessUnitName;
        this.buListArr.push(buObj);


      }


      for (var i = 0; i < this.subBusinessUnit.length; i++) {
        let sbuObj = {
          'sbuHead': "",
          "sbuHeadName": "",
          "name": ""
        }
        let empObj = this.empbasicin.find(type => type.employeeId == this.subBusinessUnit[i].sbuHead)
        console.log("Find employee : ", empObj);
        sbuObj.sbuHeadName = empObj.firstname + empObj.lastname;
        sbuObj.sbuHead = empObj.employeeId;
        sbuObj.name = this.subBusinessUnit[i].name;
        this.sbuListArr.push(sbuObj);
      }
    }
    );
  }


  getBusinessUnit() {
    var data = {
      "businessUnit": [{
      }],
      "transactionType": "GetAll"
    }
    this.psa.getBusinessUnit(data).pipe(takeUntil(this.contractSubscription)).subscribe(Response => {
      this.business = Response;
      this.businessUnit = this.business.businessUnitList;

    })
  }


  getSubBusinessUnit() {
    var data = {

      "subBusinessUnitModel": [
        {
        }],
      "transactionType": "getAll"
    }

    this.psa.getSubBusinessUnit(data).pipe(takeUntil(this.contractSubscription)).subscribe(Response => {
      this.subBusiness = Response;
      this.subBusinessUnit = this.subBusiness.subBusinessUnitList;

      console.log("subbusiness Unit", this.subBusinessUnit);

    })
  }


  // currency conversion  
  currencyoptions: any[] = ['INR', 'USD', 'EURO', 'POUNDS'];
  currency: any;
  contractvalue: any;


  getCurrency(form) {
    console.log("submit fun", form);

    this.currency = form.value.contractCurrency;
    this.contractvalue = form.value.contractValue;



    if (this.currency == 'INR') {
      console.log(this.currency, this.contractvalue);
    }
    else if (this.currency == 'USD') {

      this.contractvalue = this.contractvalue * 0.014
      console.log(this.currency, this.contractvalue);

    }
    else if (this.currency == 'EURO') {

      this.contractvalue = this.contractvalue * 0.013
      console.log(this.currency, this.contractvalue);
    }
    else if (this.currency == 'POUNDS') {

      this.contractvalue = this.contractvalue * 0.011
      console.log(this.currency, this.contractvalue);
    }

  }



}

