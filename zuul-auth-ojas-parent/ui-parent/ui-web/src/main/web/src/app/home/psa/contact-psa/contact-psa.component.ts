import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, ValidatorFn } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

import { HrmsService } from '../../services/hrms.service';
// import { ToastrManager } from 'ng6-toastr-notifications';
import swal from 'src/assets/sweetalert';
import jsPDF from 'jspdf';
import 'jspdf-autotable';
import { Subscription, Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { PsaService } from '../../services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-contact-psa',
  templateUrl: './contact-psa.component.html',
  styleUrls: ['./contact-psa.component.scss']
})
export class ContactPSAComponent implements OnInit {
  validator: ValidatorFn;
  contactSubscription: Subject<any> = new Subject();
  StateList: any;
  getcontactdataidres: any;
  userrole: any;
  userstatus: boolean;
  pages: any = 5;
  encryptedRole: any;
  response: any;
  con: any;

  constructor(private fb: FormBuilder, private ser: PsaService, private hrms: HrmsService, private authService: AuthService, private toast: NotificationService) {
    this.encryptedRole = sessionStorage.getItem('Role');
    this.userrole = this.authService.decryptData(this.encryptedRole);
    this.getDesignation()
    this.getStateListData()

  }

  ngOnInit() {

    console.log(this.userrole, "role")
    if ((this.userrole == 'ROLE_BDM') || (this.userrole == 'ROLE_BDMHEAD') || (this.userrole == 'ROLE_SALES') || (this.userrole == 'ROLE_ADMIN') || (this.userrole == 'ROLE_STAFFAUGHEAD')) {
      this.userstatus = true;

    }
    this.getAllCustomerData();
    this.getAllCustomerContactInfos()
  }

  ngOnDestroy(): any {
    this.contactSubscription.next();
    this.contactSubscription.complete();
  }
  change(data){
    var dtaa= this.customersAllData.find(s=>s.customerId==data)
    return dtaa.customerName
  }

  createBtn: any = true
  empDesignationlist: any;
  customerId: any
  contactId: any
  data: any = [];
  cid: any;
  status = false;
  private pageSize: number = 5;
  table_heading: any;
  customersdata: any;
  customersAllData: any;


  contactForm = this.fb.group({
    selectCustomer: [],
    contactName: ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z ]*$')])],
    contactPersonalEmail: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
    contactOfficialEmail: ['', [Validators.required, Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$')]],
    contactDesignation: ['', Validators.required],
    contactDepartment: ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z ]*$')])],
    contactMobileNo: ['', Validators.compose([Validators.required, Validators.pattern('[6-9]\\d{9}')])],
    alternateMobileNo: ['', Validators.compose([Validators.required, Validators.pattern('[6-9]\\d{9}')])],
    dob: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]{1,2}/[0-9]{1,2}$'), Validators.max(31)])],
    anniversary: ['', Validators.compose([Validators.required, Validators.pattern('^[0-9]{1,2}/[0-9]{1,2}$')])],
    bdm: ['', Validators.compose([Validators.required, Validators.pattern('^[a-zA-Z ]*$')])],
    address1: ['', Validators.required],
    address2: ['', Validators.required],
    address3: ['', Validators.required],
    pincode: ['', [Validators.required, Validators.pattern('^[1-9][0-9]{5}$')]],
    state: ['', Validators.required]
  })
  datestatus: boolean;

  dateValidation(e) {
    var a = String(e)
    var split = a.split('/')
    if (((Number(split[0]) >= 32)) && ((Number(split[1]) >= 12))) {
      this.toast.info("please enter valid Date")
      this.datestatus = true;
    }
    if (((Number(split[0]) <= 31)) && ((Number(split[1]) >= 13))) {
      this.toast.info("please enter valid month")
      this.datestatus = true;
    }
    if (((Number(split[0]) >= 32)) && ((Number(split[1]) <= 12))) {
      this.toast.info("please enter valid day")
      this.datestatus = true;
    } else {
      this.datestatus = false;
    }
  }
  datestatus1: boolean;
  dateValidation1(e) {
    console.log(e, "eventtriggered")
    var a = String(e)
    var split = a.split('/')
    console.log(split[0], "array1")
    console.log(split[1], "array2")
    if (((Number(split[0]) >= 32)) && ((Number(split[1]) >= 12))) {
      this.toast.info("please enter valid Date")
      this.datestatus1 = true;
    }
    if (((Number(split[0]) <= 31)) && ((Number(split[1]) >= 13))) {
      this.toast.info("please enter valid month")
      this.datestatus1 = true;
    }
    if (((Number(split[0]) >= 32)) && ((Number(split[1]) <= 12))) {
      this.toast.info("please enter valid day")
      this.datestatus1 = true;
    } else {
      this.datestatus1 = false;
    }
  }
  getAllCustomerContactInfos() {
    this.data = []
    var getallReq = {
      "customerContactInfo": [{

      }],
      "transactionType": "getall"
    }
    this.ser.getCustomerContactInfo(getallReq).pipe(takeUntil(this.contactSubscription)).subscribe(res => {

      this.response = res
      this.con = this.response.customerContactInfoList
     

      for (let i in this.con) {
        if (this.con[i].status == true) {
          this.data.push(this.con[i])
          console.log("pushed")
        }
      }

    })
  }

  saveCustomerContactInfo(contactInfo) {
    console.log("contactInfo", contactInfo)

    var contactReqObj =
    {
      "customerContactInfo": [{
        "customerId": this.customerId,
        "contactName": contactInfo.contactName,
        "designation": contactInfo.contactDesignation,
        "department": contactInfo.contactDepartment,
        "permanentMobileNumber": contactInfo.contactMobileNo,
        "alternateMobileNumber": contactInfo.alternateMobileNo,
        "personalEmail": contactInfo.contactPersonalEmail,
        "officialEmail": contactInfo.contactOfficialEmail,
        "dob": contactInfo.dob,
        "doa": contactInfo.anniversary,
        "bdm": contactInfo.bdm,
        "state": contactInfo.state,
        "pincode": contactInfo.pincode,
        "address1": contactInfo.address1,
        "address2": contactInfo.address2,
        "address3": contactInfo.address3,
        "status": true

      }],
      "transactionType": "save"
    }
    let msg
    this.ser.saveCustomerContactInfo(contactReqObj).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      msg = res
      this.toast.success(msg.message, "Success")
      this.getAllCustomerContactInfos();
    })
  }


  selectedCustomer(e) {
    this.customerId = e
    this.createBtn = false
    this.status = true
  }

  getDesignation() {
    var request = {
      "designation": [

      ],
      "sessionId": "3121",
      "transactionType": "getall"
    }
    let empDesignationDetails
    this.hrms.getEmployeeDesignation(request).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      empDesignationDetails = res;
      this.empDesignationlist = empDesignationDetails.listDesignation;
    })

  }

  getStateListData() {
    var request = {

      "states":
        [],

      "sessionId": "1234",
      "transactionType": "getAll"

    }
    var StateDetails
    this.hrms.getStateListMaster(request).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      StateDetails = res;
      this.StateList = StateDetails.statesList;
    })
  }

  // Customer Details
  getAllCustomerData() {
    var getcustomerreq = {
      "customerList": {

      },

      "transactionType": "getAll"
    }

    this.ser.getAllCustomerId(getcustomerreq).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      this.customersdata = res;
      this.customersAllData = this.customersdata.customerList;
      console.log("customers data", this.customersdata);

    })

  }

  selectedId(id) {
    console.log(id)

    this.contactId = id;
    var editByid = {
      "customerContactInfo": [{

        "contactId": id
      }],
      "transactionType": "getbyid"
    }

    let contactData
    this.ser.editCustomerContactInfoById(editByid).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      console.log(res, "response")
      contactData = res
      this.getcontactdataidres = contactData.customerContactInfoList[0];
      console.log(this.getcontactdataidres, "getbyid");
      this.contactForm.controls.contactName.setValue(contactData.customerContactInfoList[0].contactName)
      this.contactForm.controls.contactPersonalEmail.setValue(contactData.customerContactInfoList[0].personalEmail)
      this.contactForm.controls.contactOfficialEmail.setValue(contactData.customerContactInfoList[0].officialEmail)
      this.contactForm.controls.contactDesignation.setValue(contactData.customerContactInfoList[0].designation)
      this.contactForm.controls.contactDepartment.setValue(contactData.customerContactInfoList[0].department)
      this.contactForm.controls.contactMobileNo.setValue(contactData.customerContactInfoList[0].permanentMobileNumber)
      this.contactForm.controls.alternateMobileNo.setValue(contactData.customerContactInfoList[0].alternateMobileNumber)
      this.contactForm.controls.dob.setValue(contactData.customerContactInfoList[0].dob)
      this.contactForm.controls.anniversary.setValue(contactData.customerContactInfoList[0].doa)
      this.contactForm.controls.bdm.setValue(contactData.customerContactInfoList[0].bdm)
      this.contactForm.controls.state.setValue(contactData.customerContactInfoList[0].state)
      this.contactForm.controls.pincode.setValue(contactData.customerContactInfoList[0].pincode)
      this.contactForm.controls.address1.setValue(contactData.customerContactInfoList[0].address1)
      this.contactForm.controls.address2.setValue(contactData.customerContactInfoList[0].address2)
      this.contactForm.controls.address3.setValue(contactData.customerContactInfoList[0].address3)
      this.cid = contactData.customerContactInfoList[0].customerId
      for (var i = 0; i < this.customersAllData.length; i++) {
        if (this.getcontactdataidres.customerId == this.customersAllData[i].customerId) {
          this.customerId = this.customersAllData[i].customerName;
        }
      }
    })
  }

  updateCustomerContactInfo(e) {
    
    var updatecontact = {
      "customerContactInfo": [{

        "contactId": this.contactId,
        "customerId": this.cid,
        "contactName": e.contactName,
        "designation": e.contactDesignation,
        "department": e.contactDepartment,
        "permanentMobileNumber": e.contactMobileNo,
        "alternateMobileNumber": e.alternateMobileNo,
        "personalEmail": e.contactPersonalEmail,
        "officialEmail": e.contactOfficialEmail,
        "bdm": e.bdm,
        "dob": e.dob,
        "doa": e.anniversary,
        "state": e.state,
        "pincode": e.pincode,
        "address1": e.address1,
        "address2": e.address2,
        "address3": e.address3,
        "status": true
      }],
      "transactionType": "update"
    }
    let msg;
    this.ser.updateCustomerContactInfo(updatecontact).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
      msg = res
      this.toast.success(msg.message, "Success")
      this.contactForm.reset()
      this.getAllCustomerContactInfos();
      this.selectedId(this.contactId);

    })

  }


  deleteContactInfo(e) {
    var deleteContact = {
      "customerContactInfo": [{

        "contactId": e.contactId,
        "status": false


      }],
      "transactionType": "delete"
    }
    let msg
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
          this.ser.deleteContactInfo(deleteContact).pipe(takeUntil(this.contactSubscription)).subscribe(res => {
            msg = res
            this.toast.success(msg.message, "Success")
            this.getAllCustomerContactInfos();


          }, err => {
            msg = err
            this.toast.info(msg.error.error, "Info")
          })
        }
      });

  }


  number($event) {
    var key = $event.keyCode
    if (key >= 48 && key <= 57) {
      return true
    } else
      return false
  }
  download_pdf() {
    const doc = new jsPDF();
    doc.autoTable({
      head: [this.table_heading],
    })

    doc.autoTable({
      html: '#my_table',
      body: [{ halign: 'center' }],
    });
    doc.save('table.pdf');
  }
  formreset(contactForm) {
    console.log(contactForm)
    this.contactForm.controls.selectCustomer.setValue(null)
  }
}

