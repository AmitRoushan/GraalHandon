#!/usr/bin/ruby

class Student
    def initialize(id, name)
        @lang = "Ruby"
        @name = name
        @id = id
    end

    def getName
        return @name
    end

    def getID
       return @id
    end
end