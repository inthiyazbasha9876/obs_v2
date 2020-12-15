import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'contractsearch'
})
export class ContractsearchPipe implements PipeTransform {

  transform(value: any, searchString: any): any {

      if (!searchString) {
        console.log("enters")
        return value
      }
  
      return value.filter(data => {
        console.log("filters")
        const customerid = data.customerid.toString().startsWith((searchString));
        const contractid = data.contractid.toString().startsWith((searchString));
        const contractname=data.contractname.toLowerCase().startsWith(searchString.toLowerCase());
        const startdate=data.startdate.toString().trim().startsWith(searchString.toString());
        const enddate=data.enddate.toString().startsWith(searchString.toString());
        const servicetype=data.servicetype.toString().toLowerCase().trim().startsWith(searchString.toLowerCase());
        
        return (customerid + contractid + contractname + startdate +enddate + servicetype );
      })
    }



}
