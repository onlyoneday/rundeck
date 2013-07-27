package rundeck

import grails.test.GrailsUnitTestCase
import grails.test.mixin.Mock
import grails.test.mixin.TestFor

/**
 * $INTERFACE is ...
 * User: greg
 * Date: 4/19/13
 * Time: 6:06 PM
 */
@TestFor(ScheduledExecution)
@Mock([ Option, Workflow, CommandExec, Execution])
class ScheduledExecutionTest  {

    void testToMapOptions() {
        ScheduledExecution se = createBasicScheduledExecution()
        se.options.addAll([
                new Option(name: 'abc-4', defaultValue: '12', sortIndex: 4),
                new Option(name: 'bcd-2', defaultValue: '12', sortIndex: 2),
                new Option(name: 'cde-3', defaultValue: '12', sortIndex: 3),
                new Option(name: 'def-1', defaultValue: '12', sortIndex: 1),
        ])
        def jobMap = se.toMap()
        assertNotNull(jobMap)
        assertNotNull(jobMap.options)
        assertEquals(4, jobMap.options.size())
    }

    void testValidateBasic() {
        ScheduledExecution se = createBasicScheduledExecution()
        assertTrue(se.validate())
    }

    void testValidateServerNodeUUID() {
        ScheduledExecution se = createBasicScheduledExecution()
        se.serverNodeUUID = UUID.randomUUID().toString()
        assertTrue(se.validate())
    }

    void testInvalidServerNodeUUID() {
        ScheduledExecution se = createBasicScheduledExecution()
        se.serverNodeUUID = "blah"
        assertFalse(se.validate())
        assertTrue(se.errors.hasFieldErrors('serverNodeUUID'))
    }

    private ScheduledExecution createBasicScheduledExecution() {
        new ScheduledExecution(
                jobName: "test",
                groupPath: "",
                description: "",
                project: "test",
                workflow: new Workflow(
                        commands: [
                                new CommandExec(adhocRemoteString: "exec")
                        ]
                ),
                options: [],
        )
    }
}
