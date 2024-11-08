<%--
  Created by IntelliJ IDEA.
  User: 84362
  Date: 11/07/2024
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="customerListUrl" value="/admin/customer-list"/>
<html>
<head>
    <title>Danh sách Khách Hàng</title>

</head>
<body>
  <div class="main-content">
  <div class="main-content-inner">
    <div class="breadcrumbs" id="breadcrumbs">
      <script type="text/javascript">
        try {
          ace.settings.check('breadcrumbs', 'fixed')
        } catch (e) {}
      </script>

      <ul class="breadcrumb">
        <li>
          <i class="ace-icon fa fa-home home-icon"></i>
          <a href="#">Home</a>
        </li>
        <li class="active">Dashboard</li>
      </ul><!-- /.breadcrumb -->
    </div>

    <div class="page-content">
      <div class="page-header">
        <h1>
          Danh Sách khách Hàng
          <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            overview &amp; stats
          </small>
        </h1>
      </div><!-- /.page-header -->
      <!-- Tìm kiếm -->

      <div class="row">
        <div class="col-xs-12" >
          <div class="widget-box"
               style="font-family: 'Times New Roman', Times, serif;">
            <div class="widget-header" >
              <h4 class="widget-title">Tìm Kiếm</h4>

              <div class="widget-toolbar" >
                <a href="#" data-action="collapse">
                  <i class="ace-icon fa fa-chevron-up"></i>
                </a>

              </div>
            </div>
            <div class="widget-body" >
              <div class="widget-main" >
                <form:form  id="listForm" action='${customerListUrl}' method="get" modelAttribute="modalSearch">

                  <div class="row">
                    <div class="form-group">
                      <div class="col-xs-12">
                        <div class="col-xs-4">
                          <label class="name">Tên Khách Hàng</label>
                          <form:input path="fullname" class="form-control"/>
                        </div>
                        <div class="col-xs-4">
                          <label class="name">Di Động</label>
                          <form:input path="phone" class="form-control"/>
                        </div>
                        <div class="col-xs-4">
                          <label class="name">Email</label>
                          <form:input path="email" class="form-control"/>
                        </div>
                      </div>
                    </div>

                    <div class="form-group">
                      <div class="col-xs-12">

                        <div class="col-xs-4" style="margin-bottom: 15px;">
                          <security:authorize access="hasRole('MANAGER')">
                            <label class="name">Chọn nhân viên phụ trách</label>
                            <form:select path="managementStaff" class="form-control">
                              <form:option value="">---Chọn nhân viên---</form:option>
                              <c:forEach items="${listStaffs}" var="it">
                                <form:option value="${it.key}" >${it.value}</form:option>
                              </c:forEach>
                            </form:select>
                          </security:authorize>
                        </div>

                        <div class="col-xs-12">
                          <button class="btn btn-danger" id="btnSearchCustomer">
                            <i class="ace-icon fa fa-search bigger-120 "></i>
                            Tìm Kiếm
                          </button>
                        </div>

                      </div>
                    </div>

                  </div>
                </form:form>

              </div>
            </div>
          </div>

        </div>

        <div class="button_them_xoa" style="text-align: right; margin-right: 13px; ">
          <security:authorize access="hasRole('MANAGER')">
            <a href="/admin/customer-edit">
              <button class="btn btn-info" title="Thêm Khách Hàng "  style="border-radius:10px; font-size: 20px;">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-plus-fill" viewBox="0 0 16 16">
                  <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
                  <path fill-rule="evenodd" d="M13.5 5a.5.5 0 0 1 .5.5V7h1.5a.5.5 0 0 1 0 1H14v1.5a.5.5 0 0 1-1 0V8h-1.5a.5.5 0 0 1 0-1H13V5.5a.5.5 0 0 1 .5-.5"/>
                </svg>
              </button>
            </a>

            <button class="btn btn-danger" id="btnDeleteCustomers" title="Xóa Nhiều Người" style="border-radius:10px; font-size: 20px;">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-dash-fill" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M11 7.5a.5.5 0 0 1 .5-.5h4a.5.5 0 0 1 0 1h-4a.5.5 0 0 1-.5-.5"/>
                <path d="M1 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6"/>
              </svg>
            </button>
          </security:authorize>
        </div>

      </div>

      <style>
        /* CSS cho nút tìm kiếm */
        #btnSearchCustomer {
            border-radius: 5px; /* Bo góc ban đầu */
            transition: border-radius 0.3s, background-color 0.3s, transform 0.5s; /* Hiệu ứng chuyển đổi mượt */
        }

        /* Hiệu ứng khi hover */
        #btnSearchCustomer:hover {
            border-radius: 20px; /* Bo góc khi hover */
            background-color: #d9534f; /* Làm sáng màu nút */
            transform: translateY(-5px); /* Nhảy lên 5px khi hover */
        }

        /* CSS cho các nút thêm và xóa */
        .button_them_xoa button {
            border-radius: 10px; /* Bo góc ban đầu */
            font-size: 20px;
            transition: transform 0.5s, background-color 0.3s; /* Hiệu ứng di chuyển và màu */
        }

        /* Hiệu ứng khi hover */
        .button_them_xoa button:hover {
            transform: translateY(-5px); /* Nhảy lên trên 5px khi hover */
        }


       </style>


      <!--end Tìm kiếm -->


      <!-- Bảng danh sách -->
      <div class="row" style="margin-top: 70px;">
        <div class="col-xs-12" >
          <display:table name="model.listResult" cellspacing="0" cellpadding="0"
                         requestURI="${customerListUrl}" partialList="true" sort="external"
                         size="${model.totalItems}" defaultsort="2" defaultorder="ascending"
                         id="tableList" pagesize="${model.maxPageItems}"
                         export="false"
                         class="table table-fcv-ace table-striped table-bordered table-hover dataTable no-footer"
                         style="margin: 3em 0 1.5em;">
            <display:column title="<fieldset class='form-group'>
                         <input type='checkbox' id='checkAll' class='check-box-element'>
                          </fieldset>" class="center select-cell"
                            headerClass="center select-cell">
              <fieldset>
                <input type="checkbox" name="checkList" value="${tableList.id}"
                       id="checkbox_${tableList.id}" class="check-box-element"/>
              </fieldset>
            </display:column>
            <display:column headerClass="text-left" property="fullname" title="Tên Khách Hàng"/>
            <display:column headerClass="text-left" property="phone" title="Số điện thoại"/>
            <display:column headerClass="text-left" property="email" title="Email"/>
            <display:column headerClass="text-left" property="demand" title="Nhu Cầu"/>
            <display:column headerClass="text-left" property="createdBy" title="Người tạo"/>
            <display:column headerClass="text-left" property="createdDate" title="Ngày tạo"/>
            <display:column headerClass="text-left" property="status" title="Tình trạng"/>
            <display:column headerClass="col-actions" title="Thao tác">
              <div class="hidden-xs hidden-xs btn-group" >
                <!-- Nút giao tòa nhà -->
                <security:authorize access="hasRole('MANAGER')">
                  <button class="btn btn-xs btn-success" title="Giao khách hàng" onclick="assingmentCustomer(${tableList.id})">
                    <i class="ace-icon glyphicon glyphicon-list bigger-120"></i>
                  </button>
                </security:authorize>
                <a href="/admin/customer-edit-${tableList.id}" class="btn btn-xs btn-info">
                  <i class="ace-icon fa fa-pencil bigger-120"></i>
                </a>
                <security:authorize access="hasRole('MANAGER')">
                  <button class="btn btn-xs btn-danger" title="Xóa khách hàng" onclick="deleteCustomer(${tableList.id})">
                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                  </button>
                </security:authorize>
              </div>
            </display:column>
          </display:table>

        </div><!-- /.span -->
      </div>

      <style>
/* Định dạng chung cho nhóm nút */
.btn-group .btn {
  border-radius: 5px; /* Bo góc cho nút */
  border: 1px solid transparent; /* Viền mặc định */
  padding: 5px 10px; /* Khoảng cách trong nút */
  transition: all 0.3s ease; /* Hiệu ứng chuyển đổi */
}

/* Hiệu ứng khi hover vào nút */
.btn-group .btn:hover {
  transform: translateY(-5px); /* Đẩy nút lên trên 5px */
  border-color: #999; /* Màu viền khi hover */
  background-color: #f0f0f0; /* Thay đổi màu nền khi hover */
  color: #333; /* Màu chữ khi hover */
}

/* Định dạng màu nền cho từng nút */
.btn-group .btn-success {
  background-color: #28a745;
  color: white;
}

.btn-group .btn-info {
  background-color: #17a2b8;
  color: white;
}

.btn-group .btn-danger {
  background-color: #dc3545;
  color: white;
}

/* Thay đổi màu nền và viền khi hover vào từng loại nút */
.btn-group .btn-success:hover {
  background-color: #218838;
  border-color: #1c7430;
}

.btn-group .btn-info:hover {
  background-color: #138496;
  border-color: #117a8b;
}

.btn-group .btn-danger:hover {
  background-color: #c82333;
  border-color: #bd2130;
}

      </style>


      <!-- end Bảng danh sách -->
    </div>

    <!--End  Tìm kiếm -->

    <!-- PAGE CONTENT ENDS -->
  </div><!-- /.page-content -->
</div>

  <div class="modal fade" id="assingmentCustomerModal" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title">Danh Sách Nhân Viên</h5>
        <button type="button" class="close" data-dismiss="modal" style="margin-top: -27px;">&times;</button>
      </div>
      <div class="modal-body">
        <table class="table table-striped table-bordered table-hover" style="text-align: center;"
               id="staffList">
          <thead>
          <tr>
            <th style="text-align: center;">Chọn</th>
            <th style="text-align: center;">Tên Nhân Viên</th>
          </tr>
          </thead>
          <tbody class="table-group-divider">

          </tbody>
        </table>
        <input type="hidden" id="customerId">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btnAssingmentCustomer">Giao Khách Hàng</button>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Đóng</button>
      </div>
    </div>
  </div>
</div>

<!-- Tìm kiếm js  -->
  <script>
  $('#btnSearchCustomer').click(function (e) {
    e.preventDefault();
    console.log("ok") ;
    $('#listForm').submit();
  })
</script>

<%--Xây dựng hàm xóa --%>
  <script>
  function deleteCustomer(Id){
    swal({
      title: "Xác nhận xóa",
      text: "Bạn có chắc chắn xóa những dòng đã chọn",
      type: "warning",
      showCancelButton: true,
      confirmButtonText: "Xác nhận",
      cancelButtonText: "Hủy bỏ",
      confirmButtonClass: "btn btn-success",
      cancelButtonClass: "btn btn-danger"
    }).then(function (res) {
      if(res.value){
        deleteCustomers(Id) ;
      }else if(res.dismiss == 'cancel'){
        console.log('cancel');
      }
    });
    //deleteBuildings(Id) ;
  }
  $('#btnDeleteCustomers').click(function (e) {
    e.preventDefault();
    // tìm cái nào checkboxx là checked thì sẽ lấy value
    var customerids = $('#tableList').find('tbody input[type=checkbox]:checked').map(function () {
      return $(this).val();
    }).get();
    if (customerids.length === 0) {
      swal("Thông báo", "Vui lòng chọn ít nhất một khách hàng để xóa.", "warning");
      return; // Nếu không có ID nào được chọn, dừng lại
    }else{
      swal({
        title: "Xác nhận xóa",
        text: "Bạn có chắc chắn xóa những dòng đã chọn",
        type: "warning",
        showCancelButton: true,
        confirmButtonText: "Xác nhận",
        cancelButtonText: "Hủy bỏ",
        confirmButtonClass: "btn btn-success",
        cancelButtonClass: "btn btn-danger"
      }).then(function (res) {
        if(res.value){
          deleteCustomers(customerids);
        }else if(res.dismiss == 'cancel'){
          console.log('cancel');
        }
      });
    }

  })
  // xây hàm ajax chung cho xóa hết và xóa 1 cái
  function deleteCustomers(data){
    $.ajax({
      type: "DELETE",
      url :"/api/customer/" + data,
      data : JSON.stringify(data),
      contentType :"application/json",
      dataType :"JSON",
      success : function(respond, status, xhr){
        if (xhr.status === 200 || xhr.status === 204) {
          swal({
            title: "Xóa thành công!",
            text: "Khách hàng đã được xóa.",
            icon: "success"
          }).then(() => {
            window.location.href = "/admin/customer-list"; // Chuyển hướng
          });
        }
      },
      error :function(respond){
        console.log(respond) ;

      },
    })
  }
  // Xây dựng hàm load nhân viên và giao nhân viên ở đây
  function  assingmentCustomer(customerid){
    $('#assingmentCustomerModal').modal() ;
    loadStaff(customerid) ;
    // gắn biến để khi gửi về có customerid
    $('#customerId').val(customerid);
  }
  function loadStaff(customerid){
    $.ajax({
      type: "Get",
      url :"/api/customer/" + customerid +"/staffs",
      data : JSON.stringify(customerid),
      // client -> server thì dùng contenType định dạng
      contentType :"application/json",
      // server - > client thì dùng DataType định dạng
      dataType :"JSON",
      // nếu gét thì kế quả là respond và đem quăng ra view
      success : function(response){
        var row = '' ;
        $.each(response.data , function (index, item){
          row += '<tr>' ;
          row += '<td><input type="checkbox" name="check1" value="' + item.staffId + '" ' + item.checked +'></td>' ;
          row += '<td>' + item.fullName +'</td>' ;
          row += '</tr>';
        })
        //cách gọi ra bảng theo các lớp
        $('#staffList tbody').html(row) ;
        console.log("Success") ;
      },
      error :function(response){
        console.log(respondse) ;
        window.location.href ="<c:url value="/admin/customer-list?message=errol" />" ;
      },
    })
  }
  // viết API giao khách hàng cho nhân viên quản lý
  $('#btnAssingmentCustomer').click(function (e){
    e.preventDefault();
    var data = {};
    data['customerId'] = $('#customerId').val();
    // tìm cái nào checkboxx là checked thì sẽ lấy value
    var staffs = $('#staffList').find('tbody input[type=checkbox]:checked').map(function () {
      return $(this).val();
    }).get();

    data['staffs'] = staffs;
    if(data['staffs'] !=''){
      assignmentStaffCustomer(data) ;
    }else{
      swal("Thông báo", "Vui lòng chọn ít nhất một nhân vin để giao.", "warning");
    }
  }) ;
  function assignmentStaffCustomer(data){
    $.ajax({
      type: "POST",
      url :"/api/customer" + '/assignment',
      data : JSON.stringify(data),
      contentType :"application/json",
      dataType :"JSON",
      success : function(respond,status, xhr){
        if (xhr.status === 200 || xhr.status === 204) {
          swal({
            title: "Giao Thành Công!",
            text: "Các khách hàng đã được giao.",
            icon: "success"
          })
        }

      },
      error :function(respond){
        console.info("Giao ko thành cồng ")
        console.log(respond) ;
      },
    })
  }
</script>

</body>
</html>
