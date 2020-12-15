import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchProject'
})
export class SearchProjectPipe implements PipeTransform {

  transform(value: any, searchString: any): any {

    if (!searchString) {
      // console.log('no search')
      return value
    }

    return value.filter(it => {
      const customerId = it.customerId.toLowerCase().startsWith(searchString.toLowerCase());
      const contractId = it.contractId.toLowerCase().startsWith(searchString.toLowerCase());
      const projectId = it.projectId.toString().startsWith(searchString);
      const projectName = it.projectName.toLowerCase().startsWith(searchString.toLowerCase());
      // const startDate = it.startDate.toString().startsWith(searchString);
      // const endDate = it.endDate.toString().startsWith(searchString);
      // const servicecategory = it.servicecategory.toLowerCase().startsWith(searchString.toLowerCase());
      // const deliveryLocation = it.deliveryLocation.toLowerCase().startsWith(searchString.toLowerCase());
      // const resourceCount = it.projectResourceMapping.resourceCount.toString().startsWith(searchString);


      console.log(customerId + contractId);
      return (customerId + contractId + projectId + projectName);
    })
  }

}
