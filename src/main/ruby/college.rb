#!/usr/bin/ruby

$LOAD_PATH << '/home/paas/DCAP/DCAP-Sapphire/multilanguage/graalvm_poc/src/main/ruby'

require 'student'
require 'csestudent'

class College
    def initialize(name)
        @name = name
        @students = []
    end

    def getName()
        return @name
    end

    def addStudent(student)
        @students.push(student)
    end

    def getStudents()
        return @students
    end
end