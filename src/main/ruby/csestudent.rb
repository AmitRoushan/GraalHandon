#!/usr/bin/ruby

class CSEStudent < Student
    def initialize(id, name)
        super(id, name)
    end

    def getBranch()
        return "CSE"
    end

    def getLang
        @lang
    end
end