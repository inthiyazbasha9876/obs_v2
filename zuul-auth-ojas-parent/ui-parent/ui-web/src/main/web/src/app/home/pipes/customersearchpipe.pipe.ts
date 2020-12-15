import { Pipe, PipeTransform } from '@angular/core';


@Pipe({
  name: 'customersearchpipe'
})
export class CustomersearchpipePipe implements PipeTransform {
   service:any;
  transform(value: any,searchString ): any {
  
    if (!searchString) {
      console.log("enters")
      return value
    }

    return value.filter(data => {
      console.log("filters")
      const customerId = data.customerId.toString().startsWith((searchString));
      const customerName = data.customerName.toLowerCase().startsWith(searchString.toLowerCase());
      const bdmName=data.billinginfo.bdmconatctName.toLowerCase().startsWith(searchString.toLowerCase());
      const apName=data.billinginfo.apcontactName.toString().toLowerCase().startsWith(searchString.toLowerCase());
      const amconatctName=data.billinginfo.amconatctName.toString().toLowerCase().startsWith(searchString.toLowerCase());
      
      for(let i in data.servicetype){
        const services=data.servicetype[i].servicetypeid.toString().toLowerCase().startsWith(searchString.toLowerCase());
       this.service=services
      }
      return (customerId + customerName + bdmName + apName +amconatctName + this.service  );
    })
  }

}
