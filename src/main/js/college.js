function Student(id, name) {
    this.lang = "JS"
    this.id = id;
    this.name = name;
}

Student.prototype.setId = function(id) {
    this.id = id;
}

Student.prototype.setName = function(name) {
    this.name = name;
}

Student.prototype.getID = function() {
    return this.id;
}

Student.prototype.getName =function() {
    return this.name;
}


class CSEStudent extends Student {
    constructor(id, name){
        super(id, name)
    }

    getBranch(){
        return "CSE"
    }

    getLang(){
        return this.lang
    }
}

class College {
    constructor(name) {
        this.name = name;
        this.students = [];
        this.students.constructor = function constructor() {
            return [];
        }
    }

    getName() {
        return this.name;
    }
    
    addStudent(student) {
        this.students.push(student);
    }

    getStudents() {
        return this.students;
    }
}


//let college = new College("test");
//let cseStudent = new CSEStudent();

//college.addStudent(cseStudent)

//console.log(college.getStudents())
//module.exports = College;
