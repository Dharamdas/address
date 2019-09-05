package address.app.controller;

import address.app.model.Student;
import address.app.service.StudentService;
import com.datastax.driver.mapping.Result;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/student")
@Api(value = "API to perform CRUD op in a Student database maintained in apache cassandra",
        description = "This API provides the capability to perform CRUD operation in a Student " +
                "database maintained in apache cassandra", produces = "application/json")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    private static final String NO_RECORD = "Student not found for Student Id : ";


    @ApiOperation(value = "Create a new Student", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "departmentId", value = "Department Id",
                    required = true, dataType = "Integer", paramType = "header"),
            @ApiImplicitParam(name = "name", value = "Name of Student",
                    required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "address", value = "Address of Student",
                    required = true, dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseEntity < Object > createStudent(
            @RequestHeader(name = "departmentId") Integer departmentId,
            @RequestBody Student student ) {
        logger.debug("Creating Student with name :: {}", student.getName());
        ResponseEntity < Object > response = null;
        try {
            UUID studentId = studentService.createStudent(student);
            response = new ResponseEntity < Object > ("Student created successfully with Id :" +
                    studentId, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity < Object > (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }


    @ApiOperation(value = "Search Student by studentId", produces = "application/json")
    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public ResponseEntity< Object > searchStudentById(@ApiParam(name = "studentId",
            value = "The Id of the Student to be viewed",
            required = true) @PathVariable UUID studentId) {

        logger.debug("Searching for student with studentId :: {}", studentId);
        Result<Student> studentResult = null;
        ResponseEntity < Object > response = null;
        try {
            studentResult = studentService.getStudentById(studentId);
            if (studentResult == null) {
                response = new ResponseEntity < Object > (NO_RECORD + studentId, HttpStatus.OK);
            } else {
                response = new ResponseEntity < Object > (studentResult.one(), HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity < Object > (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;

    }


    @ApiOperation(value = "Delete a Student Object from Database", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "studentId", value = "StudentID to delete",
                    required = true, dataType = "UUID", paramType = "header")
    })
    @RequestMapping(method = RequestMethod.DELETE)
    public ResponseEntity < Object > delete(
            @RequestHeader(name = "studentId") UUID studentId) {
        logger.debug("Deleting Student with studentId :: {}", studentId);
        ResponseEntity < Object > response = null;
        try {
            studentService.delete(studentId);
            response = new ResponseEntity < Object > ("Student deleted successfully with Id :" +
                    studentId, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return new ResponseEntity < Object > (ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }



}
