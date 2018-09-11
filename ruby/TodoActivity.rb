class TodoActivity

  def perform()
        begin
                puts "check for start"
                w = Java.type("sapphire.wrapper.ruby.Wrapper")
                omsServer = w.getOMSServer("192.168.56.202", 22346)
                puts "OMS server #{omsServer}"

                #var todoListManagerType tlm
                tlm = w.getAppEntryPoint()
                puts "Todo Manager #{tlm}"

                tl = tlm.newTodoList("Hanks1")
                puts "Todo List  #{tl}"

                puts "#{tl.addToDo("First todo")}"
                puts "#{tl.addToDo("Second todo")}"
                puts "#{tl.addToDo("Third todo")}"
                puts "success"
        rescue StandardError => e
                puts e.message
                puts e.backtrace.inspect
        end
  end

end

tsa = TodoActivity.new()
tsa.perform
