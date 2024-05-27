package SE_team.IssueManager.controller;

/*
 * @AutoConfigureMockMvc
 * 
 * @SpringBootTest
 * 
 * @NoArgsConstructor
 * 
 * @Transactional
 * class IssueControllerTest {
 * 
 * @Autowired
 * private IssueService issueService;
 * 
 * @Autowired
 * private IssueController issueController;
 * 
 * @Autowired
 * private MockMvc mockMvc;
 * private ObjectMapper mapper=new ObjectMapper();
 * private Member member;
 * 
 * @Autowired
 * private MemberService memberService;
 * 
 * @BeforeEach
 * void setUp() {
 * MemberRequestDto.SignUpRequestDTO memberDto =
 * MemberRequestDto.SignUpRequestDTO.builder()
 * .memberId("seoyeon2")
 * .pw("1234")
 * .role(Role.ADMIN).build();
 * member= memberService.signUp(memberDto);
 * }
 * 
 * @Test
 * void createIssue() throws Exception {
 * Long reporterId=member.getId();
 * String title="title1";
 * String description="description1";
 * Priority priority=Priority.MINOR;
 * Category category=Category.CRASH;
 * 
 * IssueRequestDto.CreateIssueRequestDto
 * request=IssueRequestDto.CreateIssueRequestDto.builder()
 * .reporterId(reporterId)
 * .title(title)
 * .description(description)
 * .priority(priority)
 * .category(category).build();
 * 
 * String body=mapper.writeValueAsString(request);
 * 
 * ResultActions action=mockMvc.perform(MockMvcRequestBuilders.post(
 * "/issues/projects/{projectId}",1)
 * .content(body)
 * .contentType("application/json"));
 * 
 * action.andExpect(result -> {
 * MockHttpServletResponse response=result.getResponse();
 * System.out.println(response.getContentAsString());
 * });
 * 
 * List<Issue>
 * savedIssues=issueService.findByCondition("seoyeon2",null,null,null,null,null)
 * ;
 * for(Issue issue:savedIssues){
 * System.out.println(issue.getTitle());
 * }
 * 
 * ApiResponse<IssueResponseDto.GetIssueResponseDto>
 * issueResponse=issueController.getIssue(1L,"seoyeon2",null,null,null,null,null
 * );
 * System.out.println(issueResponse.getIsSuccess()+issueResponse.getMessage());
 * IssueResponseDto.GetIssueResponseDto
 * issueResponseDto=issueResponse.getResult();
 * System.out.println(issueResponseDto);
 * }
 * }
 * 
 */