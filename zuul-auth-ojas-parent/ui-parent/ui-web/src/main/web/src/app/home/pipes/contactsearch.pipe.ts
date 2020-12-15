import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'contactsearch'
})
export class ContactsearchPipe implements PipeTransform {

  transform(value: any,searchString ): any {
  
    if (!searchString) {
      console.log("enters")
      return value
    }

    return value.filter(data => {
      
      const contactId = data.contactId.toString().startsWith((searchString));
      const customerId = data.customerId.toString().startsWith(searchString);
      const contactName=data.contactName.toLowerCase().startsWith(searchString.toLowerCase());
      const permanentMobileNumber=data.permanentMobileNumber.toString().startsWith(searchString);
      const officialEmail=data.officialEmail.toLowerCase().startsWith(searchString.toLowerCase());
      const designation=data.designation.toLowerCase().startsWith(searchString.toLowerCase());
      const department=data.department.toLowerCase().startsWith(searchString.toLowerCase());
      const bdm=data.bdm.toLowerCase().startsWith(searchString.toLowerCase());

      return (contactId + customerId + contactName + permanentMobileNumber + officialEmail + designation + department + bdm);
    })
  }

}
