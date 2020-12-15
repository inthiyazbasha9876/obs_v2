import { Component, OnInit } from '@angular/core';
import { RmgService } from '../../services/rmg.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-employeeexperience',
  templateUrl: './employeeexperience.component.html',
  styleUrls: ['./employeeexperience.component.scss']
})
export class EmployeeexperienceComponent implements OnInit {
  key: string;

  constructor(private rmgService: RmgService, private toastr: NotificationService) { }

  ngOnInit() {
    this.getempExperiences();
  }


  empExperienceData: any;
  table: any;
  empExperienceList: any;
  getempExperiencesArr: any;
  empExperience: any;
  id: any;
  value: boolean;
  addb: boolean = true;
  private pageSize: number = 5;
  ExperienceList: any;
  noedit: boolean;
  searchfield: boolean;
  sid: any;
  status: any;



  getempExperiences() {

    var Requestdata = {
      "empExperienceList": [
        {}
      ],
      "transactionType": "getAll"
    }
    console.log("req data", Requestdata)
    this.rmgService.getempExperience(Requestdata).subscribe(res => {
      this.empExperienceData = res;
      console.log("empExperience response", this.empExperienceData)
      console.log("Get response", this.empExperienceData.empExperienceList)
      this.table = this.empExperienceData.empExperienceList;
    })
    console.log("empExperience called");
  }
  //save
  setempExperiences(value) {
    console.log(value, "request")
    var reqData =
    {

      "empExperienceList": [
        {
          "empExperience": value,
          "status": 1
        }
      ],
      "transactionType": "save"

    }
    console.log("success", reqData);

    this.rmgService.setempExperience(reqData).subscribe((response: any) => {
      this.empExperienceList = response;
      console.log("Save response", this.empExperienceList, this.empExperienceList.message);

      this.getempExperiencesArr = this.empExperienceList.empExperienceList;
      this.getempExperiences();
      this.toastr.success(response.message)

    },
      error => {
        this.toastr.info('Experience not created due to Duplicate entry')

        console.log(error)
      });

    this.id = "",
      this.empExperience = "",
      this.value = false;
  }
  //save close

  updateempExperiencetype(empExperienceTable) {

    console.log(empExperienceTable.empExperience_type, "updating value");

    var a = empExperienceTable


    var request =
    {
      "empExperienceList": [

        {
          "empExperienceId": this.id,
          "empExperience": a.empExperience_type,
          "status": true
        }
      ],
      "transactionType": "update"
    }
    console.log(request, "request")
    this.addb = true;
    this.rmgService.updatempExperience(request).subscribe((res: any) => {
      console.log(res);
      this.ExperienceList = res;
      console.log(this.ExperienceList, "updated List");
      this.getempExperiences;
      this.noedit = false;
      this.getempExperiences();
      this.toastr.success(res.message)

    },
      error => {
        console.log(error)
        this.getempExperiences();
        this.noedit = false;
        this.toastr.info('Experience not created due to Duplicate entry')

        console.log(error)
      })
    this.searchfield = false;
    this.key=""
  }


  edit(value) {
    console.log("edit", value)
    this.sid = value;
    this.id = value.empExperienceId;
    this.noedit = true;
    this.value = false;
    this.status = status;
    this.addb = false;
    this.searchfield = true;
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getempExperiences();
  }
  clear() {
    this.empExperience = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.getempExperiences();
    this.key=""
  }

  
  navigateTo(){
    this.rmgService.navigateToDashboard();
  }
  p: number;
  searchPage(){
    this.p=1;
    }
  }
