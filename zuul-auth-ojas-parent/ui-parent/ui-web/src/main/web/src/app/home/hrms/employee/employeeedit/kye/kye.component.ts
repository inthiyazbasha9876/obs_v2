import { Component, OnInit } from '@angular/core';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { DataService } from 'src/app/home/services';
import { DomSanitizer } from '@angular/platform-browser';
import { Ng2PicaService } from 'ng2-pica';
import { AuthService } from 'src/app/services/auth.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-kye',
  templateUrl: './kye.component.html',
  styleUrls: ['./kye.component.scss']
})
export class KyeComponent implements OnInit {
  isEditEnable: boolean = false;
  public eid: any;
  loggeduser: string;
  bankdetailss1: any;
  bankdata: any;
  responseMessage: any;
  today = new Date();
  karr: any;
  kidd: any;
  emmmm: any;
  ennn: any;
  passppp: any;
  aadimg: any;
  paanii: any;
  pimg: any;
  panig: any;
  aimg: any;
  passreason: any;
  aadharreason: any;
  panreason: any;
  compressor: any;
  updateddate: any;
  encryptedUsername: any;
  encryptedRole: any;
  addstatus:boolean;
  iconHide: boolean;
  buttonHide: boolean;
 
  onEdit() {
    this.isEditEnable = !this.isEditEnable;
  }

  requestobj: String | Blob;
  base64textString: String;
  b: string;
  // sanitizer: any;
  //san:any;
  resobj: any;
  pass_img: any;
  pass_img1: any;
  pass_img2: any;
  empre: Object;
  emper: Object;
  emper1: Object;
  emper2: Object;
  emperr: Object;
  emperr1: Object;
  Accesspass: any;

  constructor(private hrms: HrmsService, private dataservice: DataService, protected sanitizer: DomSanitizer, private ng2PicaService: Ng2PicaService,private authService: AuthService,private toast:NotificationService,private fb:FormBuilder) {
    
    this.getempdata();
    this.getEmpKye();
    this.eid = this.dataservice.paramId;
    console.log(this.eid,"employee id")
    // this.loggeduser = sessionStorage.getItem('UserName');
    this.encryptedUsername=sessionStorage.getItem('UserName');
    this.loggeduser=this.authService.decryptData(this.encryptedUsername);
    this.encryptedRole=sessionStorage.getItem('Role');
    this.role=this.authService.decryptData(this.encryptedRole);
    console.log(this.role,"user")
  
  }
  rolemanagerflag: boolean = true;
  role;
  hide = false;
  emphide = false;
  setUserRole;
 editBank=true;
 btnstatus:boolean=false;
 btnstatus1:boolean=false;
 btnstatus2:boolean=false;
  ngOnInit() {
    var date =new Date();
    this.getempdata();
    this.transform();
    this.getbankdetails();
    this.getEmpKye();
    this.getPassportCenter();
    this.getDateconversion(date);

    // this.role = sessionStorage.getItem("Role");
    if (this.role == "ROLE_HR") {
      this.hide = true;
    }
    if (this.role == "ROLE_USER") {
      this.emphide = true;
    }
    if (this.role == "ROLE_MANAGER") {
      this.rolemanagerflag = false;
    }
    this.setUserRole = sessionStorage.getItem("setUserRole");
    console.log('role to check user', this.setUserRole);
    if (this.setUserRole == "true") {
      this.rolemanagerflag = true;
      this.emphide = true;
this.editBank=false;
      this.hide = false;
    }

  }

  updatepan = this.fb.group({
    
    PancardUpload:[Validators.required]

  })
  updatepassport = this.fb.group({

    PassportUpload:[Validators.required]


  })
  updateadhar = this.fb.group({
   
    AadharUpload:[Validators.required]
  })

  addonboard = true;
  board;
  onb;
  onAddOnboard() {
    this.addonboard = !this.addonboard;
    this.isEditEnable = false;
    this.board = true;
    this.onb = false;
  }




  pass = true;
  empbasic: any;
  empbasicinfo: any;
  getempdata() {

    var empinfo =
    {

      "employeeInfo": [{
        "employeeId": this.eid

      }],
      "transactionType": "getbyid"

    }
    this.hrms.getempinfo(empinfo).subscribe(res => {
      this.empbasic = res;

      this.empbasicinfo = this.empbasic.employeeInfo;

      console.log("emp basic", this.empbasic);
    })
  }
  //---Employee KYE details starts--------------

  //--- getting Employee KYE details-------------------
  Location: any;
  empkye: any;
  empkyearr: any;
  savepassport: any;
  Access: boolean;
  Access1: boolean;
  Access2: boolean;
  Access3: boolean;
  getEmpKye() {

    var reqObj =
    {
      "kye":
       [ {
         "employee_Id":this.eid
        }],
      "transactionType": "getById"
    }
    console.log(reqObj,"getbyId")
    
    var kyevalue: boolean = true;
    this.hrms.getEmployeeKyeDetails(reqObj).subscribe(res => {
      console.log(res,"responsegetById")
      
      this.empkye = res;
  
      
     if(this.empkye.message=='success'){

       this.addstatus=true
     this.empkyearr = this.empkye.kyeList;
     console.log(this.empkyearr,"responsegetById")
      this.karr = this.empkyearr[this.empkyearr.length - 1];
      this.kidd = this.karr.id;

      console.log("bashaaa", this.karr);
      
       if (this.karr.passport_img != null) {
         this.passppp = this.karr.passport_img;
         
       }
       else {
         this.karr.aadhar_img = null;
       }
       if (this.karr.aadhar_img != null) {

         this.aadimg = this.karr.aadhar_img;
       }
       else {
         this.karr.pan_img = null
       }
       if (this.karr.pan_img != null) {

         this.paanii = this.karr.pan_img;
       }
       else {
        this.karr.passport_img=null;
       }

    
    
      

      if(this.empkyearr[0].passport_status=="accept"){

        this.Access = true;
        this.iconHide=true;
        this.btnstatus=true;
      }
       if(this.empkyearr[0].passport_status=="decline"){
        this.Access = false;
        this.iconHide=false;
        this.btnstatus=true;
     
      }
       if(this.empkyearr[0].passport_status=="pending"){
        this.Access = false;
        this.iconHide=false;
      }


      if(this.empkyearr[0].aadhar_status=="accept"){

        this.Access1 = true;
        this.iconHide=true;
        this.btnstatus1=true;
      }
      if(this.empkyearr[0].aadhar_status=="decline"){
        this.Access1 = false;
        this.iconHide=false;
        this.btnstatus1=true;
      }
       if(this.empkyearr[0].aadhar_status=="pending"){
       // this.buttonHide = true;
        this.Access1 = false;
        this.iconHide=false;
      }

      if(this.empkyearr[0].pan_status=="accept"){

        this.Access2 = true;
        this.iconHide=true;
        this.btnstatus2=true;
      }
       if(this.empkyearr[0].pan_status=="decline"){
        this.Access2 = false;
        this.iconHide=false;
        this.btnstatus2=true;
      }
       if(this.empkyearr[0].pan_status=="pending"){
        this.buttonHide = true;
        this.iconHide=false;
      }

   

      if (this.empkyearr.length > 0) {
        kyevalue = false;
      }
      else {
        kyevalue = true;
      }
     }
     
    
      

    },err=>{
      console.log(err,"errorresponse");
      
      
    })
    
  }


  //-- Getting Passport Deatails -------------
  passportCenterDetails: any;
  passportCenterList: any;
  passport: any;
  sample: any;
  getPassportCenter() {
    var request =
    {
      "passportList": [
      ],

      "sessionId": "323",
      "transaactionType": "getall"
    }
    this.hrms.getPassportCeneter(request).subscribe(res => {
      this.passportCenterDetails = res;
      //this.sample= this.passportCenterDetails.passportList;
      this.passportCenterList = this.passportCenterDetails.passportList;
      console.log("placessssssssss", this.passportCenterList);
    })
  }


  //num = this.passport.id



  //-- Getting Passport Deatails -------------


  //--- saving Employee KYE details-------------------
  savekyeres: any;
  savekyeresarr: any;
  kYE_Type: any;
  uan: any;
  kYE_address: any;
  passport_no: any;
  passport_date_of_Issue: any;

  passport_date_of_expiry: any;
  place_of_issue: any;
  passport_address: any;
  employee_Id: any;
  status: boolean;
  //created_by:any;
  value: boolean
  aadhar_address: any;
  pan_number: any;
  aadhar_number: any;
  files: any;
  aadharfile: any;
  passportfile: any
  panfile: any;

  passport_img: any;
  pan_img: any;
  aadhar_img: any;

  savereqObj: any;

  captialconversion1(e){
e.target.value = e.target.value.toUpperCase();
this.empkyedetails.pan_number=e.target.value;
  }
  saveEmpKye() {



    this.savereqObj =
      {
        "kye": [{
          "id": this.eid,
          "kYE_Type": this.empkyedetails.kYE_Type,
          "uan": this.empkyedetails.uan,
          "kYE_address": this.empkyedetails.kYE_address,
          "passport_no": this.empkyedetails.passport_no,
          "passport_date_of_Issue": this.empkyedetails.passport_date_of_Issue,
          "passport_date_of_expiry": this.empkyedetails.passport_date_of_expiry,
          "place_of_issue": this.empkyedetails.place_of_issue,
          "passport_address": this.empkyedetails.passport_address,
          "employee_Id": this.eid,
          "created_by": this.loggeduser,
          "passport_img": this.passport_img,
          "pan_img": this.panfile,
          "aadhar_img": this.aadharfile,
          "aadhar_address": this.empkyedetails.aadhar_address,
          "pan_number": this.empkyedetails.pan_number,
          "aadhar_number": this.empkyedetails.aadhar_number,
          "passport_status": "pending",
          "aadhar_status": "pending",
          "pan_status": "pending"


        }],
        "transactionType": "save"
      }
    //console.log("anusha" + this.empkyedetails.place_of_issue);
    this.empkyearr = this.savereqObj.kye;
    console.log("ansu", this.savereqObj);
    if(this.empkye.message=='success'){
  //  swal('You cannot add kye Once Again ,Please update if You want to change')
  this.toast.info('You cannot add kye Once Again ,Please update if You want to change')
    }
    else{

   
    if (this.empkyedetails.passport_date_of_Issue <= this.empkyedetails.passport_date_of_expiry ) {
      
      this.hrms.saveEmployeeKyeDetails(this.savereqObj).subscribe(res => {
        this.savekyeres = res;
        console.log("sssssssssssss", this.savekyeres);


        if (this.savekyeres.message == "Your Documents saved successfully") {
          // console.log();
          // swal(this.savekyeres.message, "", "success");
          this.toast.success(this.savekyeres.message)
          this.getEmpKye();
        }
        // this.getEmpKye();
      },
        err => { console.log(err) }

      )
    }

    else {
      this.getEmpKye();
      // swal("record not addedn", "", "error");
      this.toast.error("Record not added");
    }
  }
  }

  panfileSelected(event) {
    this.files = event.target.files;

    this.ng2PicaService.resize(this.files, 500, 500).subscribe((result) => {
      console.info(result);
      this.fileSelected(result)
    }, error => {
      console.error(error);
    });


  }
  fileSelected(evt) {
    var files = evt;
    var file = files;
      var reader = new FileReader();
      reader.readAsBinaryString(file);
      reader.onload = this.panHandleReaderLoaded.bind(this);
      reader.readAsBinaryString(file);
    


      
  }
  fileSelected1(evt) {
    var files = evt;
    var file = files;
   var reader = new FileReader();
    reader.readAsBinaryString(file);
     reader.onload = this.aadharHandleReaderLoaded.bind(this);
     reader.readAsBinaryString(file);
    


  }
  fileSelected2(evt) {
    var files = evt;
    var file = files;
    var reader = new FileReader();
    reader.readAsBinaryString(file);
   reader.onload = this.passportHandleReaderLoaded.bind(this);
   reader.readAsBinaryString(file);

  }



  panHandleReaderLoaded(readerEvt) {

    var binaryString = readerEvt.target.result;
    this.panfile = btoa(binaryString);

    console.log(" Pan Image Data***:", this.panfile);

  }

  aadharfileSelected(event) {

    this.files = event.target.files;
    this.ng2PicaService.resize(this.files, 500, 500).subscribe((result) => {
      console.info(result);
      this.fileSelected1(result)
    }, error => {
      console.error(error);
    });
  
  }

  aadharHandleReaderLoaded(readerEvt) {
    console.log(readerEvt, "adharvalue")

    var binaryString = readerEvt.target.result;
    this.aadharfile = btoa(binaryString);
    console.log("Aadhar Image Data***:", this.aadharfile);

  }


  passportfileSelected(event) {
    this.files = event.target.files;
    this.ng2PicaService.resize(this.files, 500, 500).subscribe((result) => {
      console.info(result);
      this.fileSelected2(result)
    }, error => {
      console.error(error);
    });
   
  }

  passportHandleReaderLoaded(readerEvt) {
    var binaryString = readerEvt.target.result;
    this.passport_img = btoa(binaryString);
     console.log("Passport Image Data***:", this.passport_img);

  }

  transform() {
    var c = this.sanitizer.bypassSecurityTrustResourceUrl(this.b);
    return c;
  }



  //--- deleting Employee KYE details-------------------
  deleteKye; any;
  deleteKyearr: any;
  isCreated: boolean = false
  deleteEmpKye(kye) {
    var deleteReqKye =
    {
      "kye": [{
        "id": kye.id,

        "updated_by": this.loggeduser
      }],
      "transactionType": "delete"
    }

    this.hrms.deleteEmployeeKyeDetails(deleteReqKye).subscribe(res => {
      this.deleteKye = res;
      console.log(this.deleteKye);
      this.deleteKyearr = this.deleteKye.kye;

      if (this.deleteKye.message == "record deleted successfully") {
        // swal(this.deleteKye.message, "", "success");
        this.toast.success(this.deleteKye.message);
        this.getEmpKye();
      }
      this.getEmpKye();
    })
  }

  //--- Updating Employee KYE details-------------------

  editkye: any;
  editkyearr: any;
  kye: any;
  isUpdate: boolean


  addeditkye(newUserFormKye) {
    newUserFormKye.reset();
    this.isUpdate = false;
    this.isCreated = true;

  }

  public empkyedetails =
    {
      "id": "",
      "kYE_Type": "",
      "uan": "",
      "kYE_address": "",
      "passport_no": "",
      "passport_date_of_Issue": "",
      "passport_date_of_expiry": "",
      "place_of_issue": "",
      "passport_address": "",
      "employee_Id": "",
      "created_by": "",
      "updated_by": "",
      "updated_date": "",
      "passport_img": "",
      "pan_img": "",
      "aadhar_img": "",
      "aadhar_address": "",
      "pan_number": "",
      "aadhar_number": "",
      "passport_status": "",
      "aadhar_status": "",
      "pan_status": ""


    }


  //----edit kye details----------------------------------------------------
  editkyeDetails: any;
  editkyebyid(kye) {
    this.isUpdate = true;
    this.isCreated = false;
    var kyeid = kye.id;
    var editempkyeobj =
    {
      "kye":
       [ {
         "employee_Id":this.eid
        }],
      "transactionType": "getById"
    }

    this.hrms.editEmployeeKyeDetails(editempkyeobj).subscribe(res => {
      this.editkye = res;
      this.editkyeDetails = this.editkye.kyeList;
      this.empkyedetails = this.editkyeDetails[0];
      console.log("this.empkyedetails", this.empkyedetails);

    })
  }

  

  //----update kye details----------------------------------------------------
  updatekyeres: any;
  updatekyeresarr: any;

  updatekye() {

    var updatekyereq =
    {
      "kye": [

        {
          "id": this.kidd,
          "kYE_Type": this.empkyearr[0].kYE_Type,
          "uan": this.empkyearr[0].uan,
          "kYE_address": this.empkyearr[0].kYE_address,
          "passport_no": this.empkyearr[0].passport_no,
          "passport_date_of_Issue": this.empkyearr[0].passport_date_of_Issue,
          "passport_date_of_expiry": this.empkyearr[0].passport_date_of_expiry,
          "place_of_issue": this.empkyearr[0].place_of_issue,
          "passport_address": this.empkyearr[0].passport_address,
          "employee_Id": this.empkyearr[0].employee_Id,
          "flag": true,
          "created_by": "18162",
          "passport_img": this.passport_img,
          "pan_img": this.empkyearr[0].pan_img,
          "aadhar_img": this.empkyearr[0].aadhar_img,
          "aadhar_address": this.empkyearr[0].aadhar_address,
          "pan_number": this.empkyearr[0].pan_number,
          "aadhar_number": this.empkyearr[0].aadhar_number,
          "passport_status": this.empkyearr[0].passport_status,
          "aadhar_status": this.empkyearr[0].aadhar_status,
          "pan_status": this.empkyearr[0].pan_status,
        }





      ],
      "transactionType": "update"
    }
    // console.log("kesh mmmmmmmm",updatekyereq);
    // debugger;


    // this.pass_img = updatekyereq.kye;
    // this.pimg=this.pass_img[0].passport_img;
    // console.log("pass img", this.pass_img[0].passport_img);
    this.karr = updatekyereq.kye;
    if (updatekyereq.kye[0].passport_img != null) {
      this.hrms.updateEmployeeKyeDetails(updatekyereq).subscribe(updateres => {
        this.emmmm = updateres;



        if (this.emmmm.message == "record updated successfully") {
          // swal(this.emmmm.message, "", "success");
          this.toast.success(this.emmmm.message);
          this.pass = false;
          this.getEmpKye();

        }

      },
        err => { console.log(err) }
      )
    }
    else {
      // swal("Enter the valid Date of expiry", "", "error")
      this.toast.error("Enter the valid Date of expiry")
      this.getEmpKye();
    }

  }

  formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }

  //update pan
  updatekyePan() {
    let date =new Date();
    var da=this.formatDate(date)
    var updatekyereq1 =
    {
      "kye": [{
        "id": this.kidd,
        "employee_Id":this.karr.employee_Id,
        "updated_date": da,
        "updated_by": this.eid,
        "pan_img":  this.panfile, 
        "pan_status":"pending"

      }],
      "transactionType": "updatePan"
    }
    console.log(updatekyereq1,"updatepan")

    
    this.karr = updatekyereq1.kye;
    console.log(updatekyereq1.kye[0].pan_img != null)
    if (updatekyereq1.kye[0].pan_img != null) {
    console.log("eners")
      this.hrms.updateEmployeeKyeDetailsbyone(updatekyereq1).subscribe(updateres => {console.log(updateres,"res")
        this.empkyearr = updateres;
      if (this.empkyearr) {
        // swal("PAN Updated Successfully ",'','success')
        this.toast.success("PAN Updated Successfully")

          this.pass = false;
          this.getEmpKye();

        }

      },
        err => { console.log(err) }
      )
    }
    else {
      // swal("PAN not Updated", "", "error")
      this.toast.error("PAN not Updated")
      this.getEmpKye();
    }

  }
  getDateconversion(date){
this.updateddate=this.formatDate(date);


  }
  //Update Aadhar Image
  updatekyeAadhar() {

    var updatekyereq2 =
    {
      "kye": [{
     
        "id": this.kidd,
        "employee_Id":this.empkyearr[0].employee_Id,
        "updated_date": this.updateddate,
        "updated_by": this.eid,
        "aadhar_img": this.aadharfile,
        "aadhar_status":"pending"
      }],
      "transactionType": "updateAadhar"
    }


   console.log(updatekyereq2,"this is for adhar")
    this.karr = updatekyereq2.kye;
    if (updatekyereq2.kye[0].aadhar_img != null) {
      this.hrms.updateEmployeeKyeDetailsbyone(updatekyereq2).subscribe(updateres => {

      
        this.empkyearr[0] = updateres;

        this.empkyearr = this.empkyearr[0];



        if (this.empkyearr) {
          // swal("Aadhar Updated Successfully ",'','success')
          this.toast.success("Aadhar Updated Successfully")

          this.pass = false;
          this.getEmpKye();

        }

      },
        err => { console.log(err) }
      )
    }
    else {
      // swal("Aadhar not Updated", "", "error")
      this.toast.error("Aadhar not Updated")
      this.getEmpKye();
    }

  }

//Update Passport Image
  updatePassport() {

    console.log("data",this.empkyearr);
    console.log(this.empkyearr[0].employee_Id,"id")
    var updatekyereq2 =
    {
      "kye": [{
     
        "id": this.kidd,
        "employee_Id":this.empkyearr[0].employee_Id,
        "updated_date": this.updateddate,
        "updated_by": this.eid,
        "passport_img": this.passport_img,
        "passport_status":"pending"
     
      }],
      "transactionType": "updatePassport"
    }


   console.log(updatekyereq2,"this is for passport")
    this.karr = updatekyereq2.kye;
    if (updatekyereq2.kye[0].passport_img != null) {
      this.hrms.updateEmployeeKyeDetailsbyone(updatekyereq2).subscribe(updateres => {console.log(updateres)

     
        this.empkyearr[0] = updateres;

        this.empkyearr = this.empkyearr[0];

        if (this.empkyearr) {
          // swal("Passport Updated Successfully ",'','success')
          this.toast.success("Passport Updated Successfully")
          this.pass = false;
          this.getEmpKye();

        }

      },
        err => { console.log(err) }
      )
    }
    else {
      // swal("Passport not updated", "", "error")
      this.toast.error("Passport not updated")
      this.getEmpKye();
    }

  }



  //Update passport accept status 
  acceptpass() {
    var upAcess =
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "passport_status": "accept",
      }],
      "transactionType": "updatePassportStatus"
    }
    console.log(upAcess,'request')

    if (upAcess.kye[0].passport_status == "accept") {
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess).subscribe(updateres => {
       console.log(updateres,"response")
        this.emper = updateres;
        if (this.emper) {
          // swal("Passport Accepted",'','success')
          this.toast.success("Passport Accepted")
          this.getEmpKye();
        }
      })

    }

  }




  declinepass() {
    var upAcess3 =
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "passport_status": "decline",
      }],
      "transactionType": "updatePassportStatus"
    }

    console.log("deline passport status", upAcess3);

    if (upAcess3.kye[0].passport_status == "decline") {
    console.log("enters")
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess3).subscribe(updateres => {
      
        this.emper = updateres;
        if (this.emper) {
          // swal("Passport Declined",'','success')
         // this.toast.success("Passport Declined")
          this.getEmpKye();
        }

      }, err => console.log(err))

    }
  }

  //Update aadhar accept status 
  acceptAadhar() {


    var upAcess1 =
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "aadhar_status": "accept",
       
      }],
      "transactionType": "updateAadharStatus"
    }

console.log(upAcess1,"request")
    if (upAcess1.kye[0].aadhar_status == "accept") {
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess1).subscribe(updateres1 => {
    console.log(updateres1,"response")
        this.emper1 = updateres1;
        if (this.emper1) {
          // swal("Aadhar accepted",'','success')
          this.toast.success("Aadhar accepted")
          this.getEmpKye();
        }
       
        this.Access1 = true;

      }, err => console.log(err))

    }

  }


  declineAadhar() {
    var upAcess4 =

    
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "aadhar_status": "decline",
      }],
      "transactionType": "updateAadharStatus"
    }

    console.log("decline aadhar status", upAcess4);

    if (upAcess4.kye[0].aadhar_status == "decline") {
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess4).subscribe(updateress => {
        this.emperr = updateress;
        if (this.emperr) {
          // swal("Aadhar declined",'','success')
          //this.toast.success("Aadhar declined")
          this.getEmpKye();
        }

      }, err => console.log(err))

    }
  }

  //Update pan status 
  acceptpPan() {


    var upAcess2 =
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "pan_status": "accept"
      }],
      "transactionType": "updatePanStatus"
    }


    console.log("pan_status", upAcess2);
      

    if (upAcess2.kye[0].pan_status == "accept") {
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess2).subscribe(updateres2 => {
        
        console.log(updateres2,"response")
        this.emper2 = updateres2;
        // swal("PAN Accepted",'','success')
        this.toast.success("PAN Accepted")
        this.getEmpKye();

        console.log("response", this.emper2);
      
        this.Access2 = true;



      }, err => console.log(err))

    }

  }


  declinePan() {
    
    var upAcess5 =
    {
      "kye": [{
        "id": this.empkyearr[0].id,
        "employee_Id":this.empkyearr[0].employee_Id,
        "pan_status": "decline"
      }],
      "transactionType": "updatePanStatus"
    }
  
    console.log("declined pan status", upAcess5);

    if (upAcess5.kye[0].pan_status == "decline") {
      this.hrms.updateEmployeeKyeDetailsbyone(upAcess5).subscribe(updateresponse => {

        this.emperr1 = updateresponse;
        if (this.emperr1) {
          // swal("PAN Declined",'','success')
          //this.toast.success("PAN Declined")
        
          this.getEmpKye();
        }

      }, err => console.log(err))

    }
  }



  downloadpp() {
    let data = this.empkyearr[0].passport_img
    var filepdf = 'data:image/png;base64,' + data;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'passport';
    a.click();
  }

  downloadAd() {
    let data = this.empkyearr[0].aadhar_img
    var filepdf = 'data:image/png;base64,' + data;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'Aadhar';
    a.click();
  }
  downloadpan() {
    let data = this.empkyearr[0].pan_img
    var filepdf = 'data:image/png;base64,' + data;
    let a = document.createElement('a');
    a.href = filepdf;
    a.download = 'pan';
    a.click();
  }




  //---- KYE details  Ends----------------------------------------------


  deleteRes: any;
  employee_bankdetails: any;
  bankdt: any;
  bankuppdate: any;
  banksave: any;
  bank_name: any;
  bank_city: any;
  bank_branch: any;
  bank_ifsc_code: any;
  bank_account_status: any;
  is_active: boolean;
  employee_id: any;
  created_by: any;
  is_bank_update: boolean;
  is_bank_save: boolean;
  employee_bankdetailsById: any;
  bank_details: any;
  updateRes: any;
  isupdateDependent: any;
  createdByDependent: any;

  //bank details starts 


  public bankdetailss = {
    "id": "",
    "bank_account_no": "",
    "bank_name": "",
    "bank_city": "",
    "bank_branch": "",
    "bank_ifsc_code": "",
    "bank_account_status": "",
    "employee_id": "",
    "is_active": "",
    "created_by": "",
    "updated_by": ""
  }
  doo;
  bankcancel() {
    this.getbankdetails();
    this.addonboard = true;
  }
  getbankdetails() {
    this.onb = true;
    var bankdetails =
    {
      "bankDetails": [{

        "employee_id": this.eid

      }
      ],
      "transactionType": "getall"
    }
    //  {
    //   "bankDetails":[

    //   ],
    // "transactionType":"getall"

    // }
    var bankvalue: boolean = true;
    this.hrms.getbankserverdetails(bankdetails).subscribe(response => {
      this.employee_bankdetails = response;
      console.log("BANK DETAILSdvsggdgdg ", this.employee_bankdetails);
      this.bankdetailss1 = this.employee_bankdetails.listBankDetails;
      this.bankdata = this.bankdetailss1[0];
      this.bankdata.is_active = this.bankdata.is_active == true ? "Active" : "In Active";
      console.log("BANK", this.bankdata);
      if (this.bankdata.bank_account_no == null) {
        this.addonboard = true;
        this.doo = false;
      }
      else {
        this.addonboard = false;
        this.doo = true;
      }
      console.log("sssssssssssssssss bank", this.bankdata.length);
      if (this.bankdata.length > 0) {
        bankvalue = false;
      }
      else {
        bankvalue = true;
      }

    })
  }

  getbankdetailsbyId(bank) {

    this.isupdateDependent = true;
    this.createdByDependent = false;
    var bbid = bank.id;
    var bankdetailsByid = {
      "bankDetails": [{
        "id": bbid

      }],
      "transactionType": "getall"

    }
    this.hrms.getbankserverdetails(bankdetailsByid).subscribe(response => {
      this.employee_bankdetailsById = response;
      this.bank_details = this.employee_bankdetailsById.listBankDetails;
      this.bankdetailss = this.bank_details[0]
      console.log("this.bankdetailss", this.bankdetailss);

    })
  }

  // Add Bank details Button 

  AddBank(newUserFormBank) {
    newUserFormBank.reset();
    this.createdByDependent = true;
    this.isupdateDependent = false;
  }


  Onsavebank(dtl) {


    var savebank =

    {
      "bankDetails": [{
        "bank_account_no": dtl.account_no,
        "bank_name": dtl.bankname,
        "bank_city": dtl.city,
        "bank_branch": dtl.branch,
        "bank_ifsc_code": dtl.ifsccode,
        "bank_account_status": "Active", //this.bankdetailss.bank_account_status
        "employee_id": this.eid,
        "is_active": dtl.isactive,
        "created_by": this.loggeduser
      }

      ],
      "transactionType": "save"
    }
    this.onb = true;
    this.hrms.savebankdetails(savebank).subscribe(response => {
      this.employee_bankdetails = response;
      console.log("resp of save", this.employee_bankdetails);

      this.getbankdetails();
      if (this.employee_bankdetails.message == "BankDetails record is saved Successfully") {
        // swal(this.employee_bankdetails.message, "", "success");
        this.toast.success(this.employee_bankdetails.message)

      }
      this.getbankdetails();
    })
  }
  deletebanktddetails(bank) {
    var delebank = {
      "bankDetails": [{
        "id": bank.id
      }

      ],
      "transactionType": "delete"
    }
    this.hrms.savebankdetails(delebank).subscribe(res => {
      this.deleteRes = res;
      if (this.deleteRes.message == "BankDetails record is deleted Successfully") {
        // swal(this.deleteRes.message, "", "success");
        this.toast.success(this.deleteRes.message);
        //this.getbankdetails();
      }
      this.getbankdetails();
    })
  }


  updatebankInfo() {

    var card2 = {
      "bankDetails": [{
        "id": this.bankdata.id,
        "bank_account_no": this.bankdata.bank_account_no,
        "bank_name": this.bankdata.bank_name,
        "bank_city": this.bankdata.bank_city,
        "bank_branch": this.bankdata.bank_branch,
        "bank_ifsc_code": this.bankdata.bank_ifsc_code,
        "bank_account_status": this.bankdata.bank_account_status,
        "employee_id": this.bankdata.employee_id,
        "is_active": this.bankdata.is_active,
        "created_by": this.loggeduser


      }

      ],
      "transactionType": "update",

    }
    console.log("result", card2);
    this.hrms.updatebankdetails(card2).subscribe(res => {
      this.responseMessage = res;
      if (this.responseMessage.message == "BankDetails record is updated Successfully") {
        // swal(this.responseMessage.message, "", "success");
        this.toast.success(this.responseMessage.message);
      }
      else {
        // swal(this.responseMessage.message, "", "error");
        this.toast.error(this.responseMessage.message);
      }
    });

  }

  updatebankdetails() {
    var user = "user";
    var updatebankdetails = {
      "bankDetails": [{
        "id": this.bankdetailss.id,
        "bank_account_no": this.bankdetailss.bank_account_no,
        "bank_name": this.bankdetailss.bank_name,
        "bank_city": this.bankdetailss.bank_city,
        "bank_branch": this.bankdetailss.bank_branch,
        "bank_ifsc_code": this.bankdetailss.bank_ifsc_code,
        "bank_account_status": this.bankdetailss.bank_account_status,
        "employee_id": this.bankdetailss.employee_id,
        "is_active": this.bankdetailss.is_active,
        "created_by": this.loggeduser


      }

      ],
      "transactionType": "update"
    }
    this.hrms.updatebankdetails(updatebankdetails).subscribe(res => {
      this.updateRes = res;

      if (this.updateRes.message == "BankDetails record is updated Successfully") {
        // swal(this.updateRes.message, "", "success");
        this.toast.success(this.updateRes.message);
        this.getbankdetails();
      }
      this.getbankdetails();
    })

  }
  declinepassport(modalform) {
    
    this.passreason = modalform.value.uname;
    console.log("passreason :", this.passreason)
    modalform.reset();
    // swal("Passport is declined", `Due to : ${this.passreason}`, "success");
    this.toast.success("Passport is declined", `Due to : ${this.passreason}`);
  }
  declineAadharreason(modalform) {
  
    this.aadharreason = modalform.value.uname;
    console.log("Aadharreason :", this.aadharreason)
    modalform.reset();
    // swal("Aadhar is declined", `Due to : ${this.aadharreason}`, "success");
    this.toast.success("Aadhar is declined", `Due to : ${this.aadharreason}`)
  }
  declinepanreason(modalform) {
    this.panreason = modalform.value.uname;
    console.log("panreason :", this.panreason)
    modalform.reset();
    // swal("Pan is declined", `Due to : ${this.panreason}`, "success");
    this.toast.success("Pan is declined", `Due to : ${this.panreason}`)
  }
}
