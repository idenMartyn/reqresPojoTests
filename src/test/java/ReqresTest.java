import org.junit.Test;
import pojo.*;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

public class ReqresTest {
    private final static String URL = "https://reqres.in";

    @Test
    public void checkAvatarAndIdTest() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                .get("/api/users?page=2")
                .then()
//                .log()
//                .all()
                .extract().body().jsonPath().getList("data", UserData.class);

//        users.forEach(x -> assertTrue(x.getAvatar().contains(x.getId().toString())));
//        assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for (int i = 0; i < avatars.size(); i++) {
            assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void successRegisterTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        RegisterSuccessfulRequest user = new RegisterSuccessfulRequest("eve.holt@reqres.in", "pistol");

        RegisterSuccessfulResponse successReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then()
                        .extract().as(RegisterSuccessfulResponse.class);

        assertNotNull(successReg.getId());
        assertEquals(id, successReg.getId());
        assertEquals(token, successReg.getToken());
    }

    @Test
    public void unsuccessRegisterTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        String error = "Missing password";
        RegisterUnsuccessfulRequest user = new RegisterUnsuccessfulRequest("sydney@fife");
        RegisterUnsuccessfulResponse unsuccessReg = given()
                .body(user)
                .when()
                .post("/api/register")
                .then()
                .extract().as(RegisterUnsuccessfulResponse.class);
        assertEquals(error, unsuccessReg.getError());
    }

    @Test
    public void sortedYearsTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<ColorsData> colors = given()
                .when()
                .get("/api/unknown")
                .then()
                .extract().body().jsonPath().getList("data", ColorsData.class);

        List<Integer> years = colors.stream().map(ColorsData::getYear).collect(Collectors.toList());
        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        assertEquals(sortedYears, years);
    }

    @Test
    public void deleteUserTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecUnique(204));
        given()
                .when()
                .delete("/api/users/2");
    }

    @Test
    public void timeTest(){
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        UserTime user = new UserTime("morpheus", "zion resident");

        UserTimeResponse response = given()
                .body(user)
                .when()
                .put("/api/users/2")
                .then().extract().as(UserTimeResponse.class);
        String regex = "(.{5})$";
        String currentTime = Clock.systemUTC().instant().toString().replaceAll(regex, "");
        assertEquals(currentTime, response.getUpdatedAt().replaceAll(regex, ""));
    }
}
