import { Component, OnInit } from '@angular/core';
import { PsaService } from 'src/app/home/services/psa.service';
import { NotificationService } from 'src/app/services/toastr-notification/toastr-notification.service';

@Component({
  selector: 'app-budgetlist',
  templateUrl: './budgetlist.component.html',
  styleUrls: ['./budgetlist.component.scss']
})
export class BudgetlistComponent implements OnInit {


  value: boolean;
  table: any;
  id: any;
  status: any;
  addb = true;
  masterList: any;
  noedit: boolean;
  searchfield: boolean;
  private pageSize: number = 5;
  sid: any;
  budgetlist: any;
  budgetList: string;
  budgetlistarr: any;
  budget: any;
  editname: any;
  key: string;

  constructor(private psa: PsaService,private toast:NotificationService) { }
  
  ngOnInit() {
    this.getbudgetlist();
  }
  //getall
  getbudgetlist() {


    var Requestdata = {
      "budgetList": [{


      }],
      "transactionType": "getall"
    }
    console.log("success", Requestdata)
    this.psa.getbudgetlist(Requestdata).subscribe(responce => {
      this.budgetlist = responce;

      console.log("Get response", this.budgetlist)
      console.log("Get response", this.budgetlist.budgetList)
      this.table = this.budgetlist.budgetList;
    })

  }
  //get all close

  //save
  setbudgetlist() {
    console.log("s", this.budget)
    var reqData =
    {
      "budgetList": [{

        "budget": this.budget,
        "status": true

      }],
      "transactionType": "save"

    }

    console.log("reqdata", reqData);

    this.psa.setbudgetlist(reqData).subscribe((response: any) => {
      this.budgetlist = response;
      console.log("Save response", this.budgetlist);

      if (this.budgetlist.message == "Budget  details  saved successfully") {

        // swal("Budget list  has saved successfully", "","success");
        this.toast.success("Budget list  has saved successfully")
        this.getbudgetlist();
      }

    },
      error => {
        // swal("Duplicates are not allowed","","error");
        this.toast.error("Duplicates are not allowed")

        this.budgetlistarr = this.budgetlist.budgetList;
        this.getbudgetlist();

      });
    this.id = "",
      this.budget = "",
      this.value = false;

  }
  //save close

  updatebudgetlist(budgetListtable) {

    // this.searchfield=false;

    console.log("budgetListtable", budgetListtable);
    var updateRequestData =
    {
      "budgetList": [{
        "id": this.sid,
        "budget": budgetListtable.budget,
        "status": this.status

      }],
      "transactionType": "update"

    }
    console.log("success", updateRequestData)
    this.addb = true;
    this.psa.updatebudgetlist(updateRequestData).subscribe((res: any) => {
      this.masterList = res;
      console.log(this.masterList);
      if (this.masterList.message == "BudgetList details updated successfully") {
        // swal("Budget List has updated successfully", "", "success");
        this.toast.success("Budget List has updated successfully")
        this.getbudgetlist();
        this.noedit = false;
      }
      this.getbudgetlist();
    },
      error => {
        // swal("Duplicates are not allowed","","error");
        this.toast.error("Duplicates are not allowed")
        this.getbudgetlist();
        this.noedit = false;
      })
    this.searchfield = false;
    this.key=""
  }


  edit(id,name, status) {
    console.log("edit", id)
    this.sid = id;
    this.noedit = true;
    this.value = false;
    this.status = status
    this.addb = false;
    this.searchfield = true;
    this.editname=name;
  }




  cancelbulist() {
    this.value = false;
    this.addb = true;
    this.getbudgetlist();
  }
  clear() {
    this.budget = "";
  }
  cancel() {
    this.noedit = false;
    this.searchfield = false;
    this.addb = true;
    this.getbudgetlist();
    this.key=""
  }
 
  navigateTo(){
    this.psa.navigateToDashboard();
  }

  p: number;
  searchPage(){
    this.p=1;
    }
}
