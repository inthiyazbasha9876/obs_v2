import { Injectable } from '@angular/core';

@Injectable({
providedIn: 'root'
})
export class DictionaryService {

words:any=['first_name','last_name','middle_name','pan_number','tan_number','geo_locations','customer_status','created_date','updated_date','created_by','updated_by','city_location','states_location','registered_pincode','rate_type','billing_type','service_type','contract_id','customer_id','contract_name','start_date','end_date','contract_value','contract_owner','contract_company','contract_currency','delivery_location'
,'executing_company','document_type','document_stage','multi_location_delivery','service_type_id','project_manager','alternate_employee_id'
]
constructor() { }

formatDate(date) {
    var year = date.getFullYear().toString();
    var month = (date.getMonth() + 101).toString().substring(1);
    var day = (date.getDate() + 100).toString().substring(1);
    return year + "-" + month + "-" + day;
  }
}
