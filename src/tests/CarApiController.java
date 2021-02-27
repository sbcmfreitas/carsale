

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ManagerApplication.class,WebSecurityConfig.class} )
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class CarApiControllerTest {
   
    
    @Before
	public void setup() throws Exception {
		   
	}

}
