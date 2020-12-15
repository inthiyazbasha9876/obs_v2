import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/home/services';
import { HrmsService } from 'src/app/home/services/hrms.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { FormBuilder, FormArray } from '@angular/forms';
import { TimesheetService } from 'src/app/home/timesheetmanagement/tmsservices/timesheet.service';
import { PsaService } from 'src/app/home/services/psa.service';
import { HttpClient } from '@angular/common/http';
import { AuthService } from 'src/app/services/auth.service';
import html2canvas from 'html2canvas';
import jsPDF from 'jspdf';
declare var $;
@Component({
  selector: 'app-profile-view',
  templateUrl: './profile-view.component.html',
  styleUrls: ['./profile-view.component.scss']
})
export class ProfileViewComponent implements OnInit {
  private profile: Subject<any> = new Subject();
  employeeId: any

  proData: any = true
  expData: any = true
  sumData: any = true
  url: any

  profileData: any
  projects: any = []
  empSkills: any
  empData: any
  empEducation: any
  resumeObject = {
    "summary": "",
    "experience": [],
    "responsibilties": []
  }


  resumeForm = this.fb.group({
    executiveSummary: [''],
    addExperience: this.fb.array([
      this.fb.group({
        experience: ['']
      }),
    ]),
  })

  projectForm = this.fb.group({
    proName: [''],
    proId: [''],
    role: [''],
    description: [''],
    addResponsibility: this.fb.array([
      this.fb.group({
        responsibiltiy: ['']
      }),
    ]),
  })


  constructor(private auth: AuthService, private dataService: DataService, private hrms: HrmsService, private fb: FormBuilder, private tms: TimesheetService, private psa: PsaService, private http: HttpClient) {
    this.url = window.location.href
    this.employeeId = this.dataService.paramId
  }

  public async ngOnInit() {
    await this.getBasicInfo()
    await this.getOnboarding()
    await this.getEducation()
    await this.getProject()
    await this.getSkills()
  }

  ngOnDestroy(): any {
    this.profile.next();
    this.profile.complete();
  }

  addExperience() {
    (this.resumeForm.get('addExperience') as FormArray).push(this.fb.group({
      experience: ['']
    }))
  }

  addProject() {
    // this.project.title="Ojas Business Suite"
    // this.project.role="Manager"
    // this.project.description="hello this is obs project"
    // this.project.responsibilties=this.fb.group({
    //   addresponsibility: this.fb.array([
    //     this.fb.group({
    //       responsibiltiy: ['']
    //     }),
    //   ])
    // })
    // this.projects.push(this.project)
  }

  addResponsibity() {
    (this.projectForm.controls.addResponsibility as FormArray).push(this.fb.group({
      responsibiltiy: ['']
    }))
  }

  public getBasicInfo() {
    this.empData = null
    this.clearExperience()
    var response
    var reqObj =
    {
      "employeeInfo": [{
        "employeeId": this.employeeId

      }],
      "transactionType": "getbyid"
    }
    // this.hrms.getempinfo(reqObj).pipe(takeUntil(this.profile)).subscribe(res => {
    //   console.log("get info detials", res);

    // })
    this.http.post("http://192.168.3.18:8093/get", reqObj).subscribe(res => {
      console.log("basic info", res);
      response = res
      this.empData = response.employeeInfo[0]
      var profile = this.auth.decryptData(response.employeeInfo[0].profile)
      this.profileData = profile
      console.log("my profile data", profile);
      this.resumeForm.controls.executiveSummary.setValue(profile.summary)
      var c = 0
      for (let i in profile.experience) {
        if (c == 0) {
          console.log(this.resumeForm.get('addExperience') as FormArray);
          (this.resumeForm.get('addExperience') as FormArray).controls[c].get('experience').setValue(profile.experience[i].experience)
          c++
        }
        else if (c > 0) {
          this.addExperience()
          console.log(this.resumeForm.get('addExperience') as FormArray);
          (this.resumeForm.get('addExperience') as FormArray).controls[c].get('experience').setValue(profile.experience[i].experience)
          c++
        }
      }
    }, ere => {
      console.log(ere);

    })
  }

  public getOnboarding() {
    var response
    var reqObj =
    {
      "employmentDetails": [{
        "employeeId": this.employeeId
      }],
      "transactionType": "getAll"
    }
    this.hrms.getonboardingdetails(reqObj).pipe(takeUntil(this.profile)).subscribe(res => {
      console.log("get boarding detials", res);

    })

  }

  public getSkills() {
    this.empSkills = null
    var response
    var reqObj =
    {
      "skillInfoModel": [{
        "employee_id": this.employeeId
      }],
      "transactionType": "getByEmpId"
    }
    this.hrms.getSkilbyId(reqObj).pipe(takeUntil(this.profile)).subscribe(res => {
      console.log("get skills detials", res);
      response = res
      this.empSkills = response.getSkillInfoList
    })
  }

  public getEducation() {
    var response
    var reqObj =
    {
      "employeeEducationDetailsList": [
        {
          "employeeId": this.employeeId
        }
      ],
      "transactionType": "getById"
    }
    this.hrms.getEmpEduDetails(reqObj).pipe(takeUntil(this.profile)).subscribe(res => {
      console.log("get education detials", res);
      response = res
      this.empEducation = response.employeeEducationDetailsList
    })
  }

  public getProject() {
    this.projects = []
    var response
    var projectName
    var reqObj =
    {
      "projectDetailsList": [{
        "employeeId": this.employeeId
      }],
      "transactionType": "getById"
    }

    this.http.post("http://192.168.3.18:8083/get",reqObj).subscribe(res=>{
      console.log("projects",res);
    })
  }



  public psaPro() {
    this.projects = []
    var response
    var projectName
    var reqObj =
    {
      "projectlist":
      {
        "empId": this.employeeId

      },
      "transactiontype": "getprojects"
    }
    this.tms.getProjectDetails(reqObj).subscribe(res => {
      console.log("project id", res);
      response = res
      for (let i in response.empIdList) {

        var reqObj = {
          "projectInfo": {
            "projectId": response.empIdList[i]
          },
          "transactionType": "getByProId"
        }
        this.psa.getIdProject(reqObj).subscribe(res => {
          console.log("res", res, this.profileData);
          projectName = res
          for (let i in this.profileData.responsibilties) {
            if (this.profileData.responsibilties[i].proId == projectName.projectList[0].projectId) {
              var project = new Object({
                title: projectName.projectList[0].projectName,
                proId: projectName.projectList[0].projectId,
                role: this.empData.title,
                description: projectName.projectList[0].projectDescription,
                responsibilities: this.profileData.responsibilties[i].responsibilities
              })
              this.projects.push(project)
            }
          }

          console.log(this.projects);
        })
      }

    })
  }

  editSummary() {
    this.sumData = false
  }

  editExperience() {
    this.expData = false
  }

  editProject(e) {
    this.clearResponsibilities()
    this.proData = false
    console.log(e);
    this.projectForm.controls.proName.setValue(e.title)
    this.projectForm.controls.role.setValue(e.role)
    this.projectForm.controls.description.setValue(e.description)
    this.projectForm.controls.proId.setValue(e.proId)
    var c = 0
    for (let i in e.responsibilities) {
      if (c == 0) {
        console.log(this.projectForm.get('addResponsibility') as FormArray);
        (this.projectForm.get('addResponsibility') as FormArray).controls[c].get('responsibiltiy').setValue(e.responsibilities[i].responsibiltiy)
        c++
      }
      else if (c > 0) {
        this.addResponsibity()
        console.log(this.projectForm.get('addResponsibility') as FormArray);
        (this.projectForm.get('addResponsibility') as FormArray).controls[c].get('responsibiltiy').setValue(e.responsibilities[i].responsibiltiy)
        c++
      }
    }
  }

  saveProfile() {
    console.log("in save profile", this.projects);
    var array = []
    this.sumData = true
    this.resumeObject.summary = this.resumeForm.value.executiveSummary
    this.resumeObject.experience = this.resumeForm.value.addExperience
    for (let i in this.projects) {
      var obj = new Object({ proId: this.projects[i].proId, responsibilities: this.projects[i].responsibilities })
      array.push(obj)
    }
    this.resumeObject.responsibilties = array
    var finalObj = this.auth.encryptData(this.resumeObject)
    var reqObj = {
      "employeeInfo": [{
        "employeeId": this.employeeId,
        "profile": finalObj
      }],
      "transactionType": "updateProfile"
    }
    this.http.post("http://192.168.3.18:8093/set", reqObj).subscribe(res => {
      console.log("res", res);
      this.ngOnInit()
      this.expData = true
    })
  }

  saveProject() {
    console.log(this.projectForm);
    this.proData = true
    for (let i in this.projects) {
      if (this.projects[i].proId == this.projectForm.value.proId) {
        this.projects[i].responsibilities = this.projectForm.value.addResponsibility
      }
    }
    console.log(this.projects);
    this.saveProfile()
  }

  deleteResposibility(i) {
    console.log(i);
    console.log(this.resumeForm.controls.addResponsibility);
    (this.resumeForm.get('addResponsibility') as FormArray).removeAt(i);
  }

  deleteExperience(i) {
    console.log(i);
    console.log(this.projectForm.controls.addExperience);
    (this.projectForm.get('addExperience') as FormArray).removeAt(i);
  }

  clearExperience() {
    this.resumeForm.reset()
    let array = <FormArray>this.resumeForm.get('addExperience')
    while (array.length) {
      array.removeAt(array.length - 1);
    }
    this.addExperience();
  }

  clearResponsibilities() {
    this.projectForm.reset()
    let array = <FormArray>this.projectForm.get('addResponsibility')
    while (array.length) {
      array.removeAt(array.length - 1);
    }
    this.addResponsibity();
  }

  scroll(e) {
    var elmnt = document.getElementById(e);
    elmnt.scrollIntoView();
  }

  downloadResume() {
    var data = document.getElementById('resume');

    html2canvas(document.querySelector("#resume")).then(canvas => {
      var imgWidth = 300;
      var imgHeight = canvas.height * imgWidth / canvas.width;
      const contentDataURL = canvas.toDataURL('image/jpeg')
      let pdf = new jsPDF('p', 'mm', 'a4');
      var position = 10;
      pdf.addImage(contentDataURL, 'jpeg', 0, position)
      pdf.save('resume.pdf');
    });
  }

  // downloadResume(quality = 1) {
  //   const filename  = 'ThisIsYourPDFFilename.pdf';

  //   html2canvas(document.querySelector('#resume'), 
  //               {scale: quality}
  //            ).then(canvas => {
  //     let pdf = new jsPDF('p', 'mm', 'a4');
  //     pdf.addImage(canvas.toDataURL('image/png'), 'PNG', 0, 0, 211, 298);
  //     pdf.save(filename);
  //   });
  // }

}
