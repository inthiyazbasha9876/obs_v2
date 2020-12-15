import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
	name: 'searchDirectory'
})
export class SearchDirectoryPipe implements PipeTransform {

	transform(value: any[], searchString: string) {

		if (!searchString) {
			console.log('no search')
			return value
		}

		return value.filter(it => {
			const empId = it.employeeId.toString().includes(searchString)
			const firstname = (it.firstname.toLowerCase().concat(" "+it.lastname.toLowerCase())).includes(searchString.toLowerCase())
			// const lastname = it.lastname.toLowerCase().includes(searchString.toLowerCase())
			const mail = it.officialEmail.toLowerCase().includes(searchString.toLowerCase())
			const mobile = it.personalMobileNo.toString().includes(searchString)

			return (empId + firstname  + mobile + mail);
		})
	}

}
