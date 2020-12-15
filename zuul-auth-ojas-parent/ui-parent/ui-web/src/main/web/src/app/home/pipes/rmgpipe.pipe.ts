import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'rmgpipe'
})
export class RmgpipePipe implements PipeTransform {

  // transform(value: any, ...args: any[]): any {
  //   return null;
  // }
  transform(value: any,searchString ): any {
  
    if (!searchString) {
     return value
    }

    return value.filter(data => {
      
      const bookingId = data.bookingId.toString().startsWith((searchString));
      const resourceType = data.resourceType.toLowerCase().startsWith(searchString.toLowerCase());
      const projectId=data.projectId.toString().startsWith(searchString);
      const projectName=data.projectName.toLowerCase().startsWith(searchString.toLowerCase());
  

      return (bookingId + resourceType + projectId + projectName);
    })
  }
}
