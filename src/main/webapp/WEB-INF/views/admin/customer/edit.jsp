<%--
  Created by IntelliJ IDEA.
  User: 84362
  Date: 11/07/2024
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css">
<%@include file="/common/taglib.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:url var="customerEditURL" value="/admin/customer-edit"/>
<c:url var="customerListURL" value="/admin/customer-list"/>

<html>
<head>
    <title>
         <c:if test="${empty modalAdd.id}">
          Thêm Khách hàng mới
         </c:if>

         <c:if test="${not empty modalAdd.id}">
           Sửa khách hàng
         </c:if>
    </title>
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
                <c:if test="${empty modalAdd.id}">
                    <li class="active">Thêm Khách hàng mới</li>
                </c:if>
                 <c:if test="${not empty modalAdd.id}">
                     <li class="active">Sửa khách hàng</li>
                 </c:if>
            </ul><!-- /.breadcrumb -->
        </div>

        <div class="page-content">
            <div class="page-header">
                <h1 style="font-weight: bold;">
                    Thông tin khách hàng
                </h1>
            </div><!-- /.page-header -->

            <div class="row" style="font-family: 'Times New Roman', Times, serif;">
                <div class="col-xs-12">
                    <form:form action='${customerEditURL}' method="post" modelAttribute="modalAdd" id="form_edit">
                        <div class="form-group">
                            <label class="col-xs-3 title_text">Tên khách hàng</label>
                            <div class="col-xs-9">
                                <form:input path="fullname" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 title_text">Số điện thoại</label>
                            <div class="col-xs-9">
                                <form:input path="phone" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 title_text">Email</label>
                            <div class="col-xs-9">
                                <form:input path="email" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 title_text">Tên công ty</label>
                            <div class="col-xs-9">
                                <form:input path="companyName" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 title_text">Nhu cầu</label>
                            <div class="col-xs-9">
                                <form:input path="demand" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-xs-3 title_text">Tình trạng</label>
                            <div class="col-xs-9">
                                <form:input path="status" class="form-control"/>
                            </div>
                        </div>

                        <div class="form-group" style="text-align: center;">
                            <c:if test="${empty modalAdd.id}">
                                <button type="button" class="btn btn-success" data-toggle="button"
                                        style="font-size: 20px;" id="btnAddorUpdateCustomer">Thêm Khách hàng</button>
                                <button type="button"  id="btnHuy" class="btn btn-danger" data-toggle="button"
                                        style="margin-left: 15px; font-size: 20px;">Hủy Thao Tác</button>

                            </c:if>

                            <c:if test="${not empty modalAdd.id}">
                                <button type="button" class="btn btn-success" data-toggle="button"
                                        style="font-size: 20px;" id="btnAddorUpdateCustomer">Cập nhật Khách Hàng</button>
                                <button type="button" id="btnHuy" class="btn btn-danger" data-toggle="button"
                                        style="margin-left: 15px; font-size: 20px;">Hủy Thao Tác</button>

                                </c:if>
                        </div>
<%--                            Để gửi về kèm id nếu là sửa--%>
                        <form:hidden path="id" id="customerId"/>
                        </form:form>
                </div>
            </div>
        </div>

<%--        css cho phâần button --%>
    <style>
    /* Thêm vào tệp CSS của bạn */
    #btnAddorUpdateCustomer, #btnHuy {
        font-size: 20px;
        border-radius: 3px;
        transition: border-radius 0.3s;
    }

    /* Hiệu ứng khi hover vào nút */
    #btnAddorUpdateCustomer:hover, #btnHuy:hover {
        border-radius: 20px;
    }

    </style>


    <c:if test="${not empty TransactionType}">
        <c:forEach items="${TransactionType}" var="item">
            <div class="page-header">
                <h1 style="font-weight: bold; margin:0px 0px 15px 10px">${item.value}</h1>
                <hr style="font-weight: bold;">
               <button class="btn btn-lg btn-success btn-custom" style="margin-left: 10px; margin-bottom: 10px" onclick="addTransaction('${item.key}','${modalAdd.id}')">
                    <svg style="margin-right: 4px; margin-left: 0px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-flower3" viewBox="0 0 16 16">
                      <path d="M11.424 8c.437-.052.811-.136 1.04-.268a2 2 0 0 0-2-3.464c-.229.132-.489.414-.752.767C9.886 4.63 10 4.264 10 4a2 2 0 1 0-4 0c0 .264.114.63.288 1.035-.263-.353-.523-.635-.752-.767a2 2 0 0 0-2 3.464c.229.132.603.216 1.04.268-.437.052-.811.136-1.04.268a2 2 0 1 0 2 3.464c.229-.132.489-.414.752-.767C6.114 11.37 6 11.736 6 12a2 2 0 1 0 4 0c0-.264-.114-.63-.288-1.035.263.353.523.635.752.767a2 2 0 1 0 2-3.464c-.229-.132-.603-.216-1.04-.268M9 4a2 2 0 0 1-.045.205q-.059.2-.183.484a13 13 0 0 1-.637 1.223L8 6.142l-.135-.23a13 13 0 0 1-.637-1.223 4 4 0 0 1-.183-.484A2 2 0 0 1 7 4a1 1 0 1 1 2 0M3.67 5.5a1 1 0 0 1 1.366-.366 2 2 0 0 1 .156.142q.142.15.326.4c.245.333.502.747.742 1.163l.13.232-.265.002a13 13 0 0 1-1.379-.06 4 4 0 0 1-.51-.083 2 2 0 0 1-.2-.064A1 1 0 0 1 3.67 5.5m1.366 5.366a1 1 0 0 1-1-1.732l.047-.02q.055-.02.153-.044.202-.048.51-.083a13 13 0 0 1 1.379-.06q.135 0 .266.002l-.131.232c-.24.416-.497.83-.742 1.163a4 4 0 0 1-.327.4 2 2 0 0 1-.155.142M9 12a1 1 0 0 1-2 0 2 2 0 0 1 .045-.206q.058-.198.183-.483c.166-.378.396-.808.637-1.223L8 9.858l.135.23c.241.415.47.845.637 1.223q.124.285.183.484A1.3 1.3 0 0 1 9 12m3.33-6.5a1 1 0 0 1-.366 1.366 2 2 0 0 1-.2.064q-.202.048-.51.083c-.412.045-.898.061-1.379.06q-.135 0-.266-.002l.131-.232c.24-.416.497-.83.742-1.163a4 4 0 0 1 .327-.4q.07-.074.114-.11l.041-.032a1 1 0 0 1 1.366.366m-1.366 5.366a2 2 0 0 1-.155-.141 4 4 0 0 1-.327-.4A13 13 0 0 1 9.74 9.16l-.13-.232.265-.002c.48-.001.967.015 1.379.06q.308.035.51.083.098.024.153.044l.048.02a1 1 0 1 1-1 1.732zM8 9a1 1 0 1 1 0-2 1 1 0 0 1 0 2"/>
                    </svg>
                    Add
               </button>

                <div class="col-xs-12" >
                    <table id="simple-table" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>Ngày Tạo</th>
                            <th>Người Tạo</th>
                            <th>Chi tiết giao dịch</th>
                            <th>Thao Tác</th>
                        </tr>
                        </thead>

                        <tbody>
                        <c:forEach items="${listTranById}" var="itemList">
                            <c:if test="${item.key == itemList.code}">
                                <tr>
                                <td>${itemList.createdDate}</td>
                                <td>${itemList.createdBy}</td>
                                <td>${itemList.note}</td>
                                <td>
                                    <button class="btn btn-xs btn-danger" onclick="deleteTran('${itemList.id}')">
                                        <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                    </button>

                                </td></tr>
                            </c:if>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </c:forEach>
    </c:if>


        <!-- PAGE CONTENT ENDS -->
    </div><!-- /.page-content -->

</div>

<style>
/* CSS cho nút */
.btn-custom {
    border-radius: 8px; /* Bo góc mặc định */
    transition: all 0.3s ease; /* Hiệu ứng mượt khi thay đổi */
}

.btn-custom:hover {
    border-radius: 12px; /* Bo góc khi hover */
    transform: translateY(-2px); /* Đẩy nút lên một chút khi hover */
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2); /* Tạo hiệu ứng bóng đổ */
}

</style>

<script>
    $('#btnAddorUpdateCustomer').click(function (e){
        e.preventDefault();
        var data = {} ;
        var formData = $('#form_edit').serializeArray();
        console.log(formData) ;
        $.each(formData,function (k,v){
            data["" +v.name + ""] = v.value ;
        }) ;
        if(data['fullname']!="")
        {
            addorupdate(data) ;
            window.location.href = "${customerListURL}";
        }
        else
        {
            window.location.href = "${customerEditURL}?fullname=required";
            alert("Bạn cần điền đầy đủ thông tin ");
        }

    })
    function addorupdate(data){
        $.ajax({
            type :"POST",
            url : "/api/customer",
            data : JSON.stringify(data),
            contentType :"application/json",
            dataType:"JSON",
            success:function (respond,status, xhr) {
                if(xhr.status === 200 || xhr.status === 204){
                   swal({
                       title : "Thông báo",
                       icon :"success",
                       text :"Bạn Đã Thêm Hoặc Cập Nhật Thành Công",
                       confirmButtonText: "OK",
                       confirmButtonClass: "btn btn-success"
                   })
                }
            },
            error : function (respond) {
                console.log("fail") ;
            }

        })
    }
    $('#btnHuy').click(function (){
        window.location.href="/admin/customer-list" ;
    })
    // Hàm thêm giao dịch
    function addTransaction(code,customerId){
        $('#TransactionCustomerModal').modal() ;
        // gắn các giá trị cho biến ẩn
        $('#customerId').val(customerId) ;
        $('#code').val(code) ;
    }
    // Sử lý button thêm
    $('#btnaddTransaction').click(function (e){
        e.preventDefault();
        var data = {};
        data['customerid'] =$('#customerId').val() ;
        data['code']=$('#code').val() ;
        data['note']=$('#note').val() ;
        console.log(data) ;
        $.ajax({
            type: "POST",
            url :"/api/customer" +"/transactionof",
            data:JSON.stringify(data),
            contentType: "application/json",
            dataType: "JSON",
            success :function (respond,status,xhr){
                if(xhr.status === 200 || xhr.status === 204 ){
                    swal({
                        title: "Thông báo",
                        icon :"success",
                        text:"Thêm giao dịch thành công",
                        confirmButtonText: "OK",
                        confirmButtonClass: "btn btn-success"
                    })
                }
            },
            errors:function (respond){
                console.log(respond) ;
            }
        })
    })
    function deleteTran(id){
        $.ajax({
            type: "DELETE",
            url:"/api/customer" +"/transactionof/"+ id ,
            data:JSON.stringify(id) ,
            contentType:"application/json",
            dataType:"JSON",
            success:function (respond,status,xhr){
                if(xhr.status === 200 ||xhr.status ===204){
                    swal({
                        title:"Thông Báo",
                        icon:"success",
                        text:"Xóa Thành Công",
                        confirmButtonText: "OK",
                        confirmButtonClass: "btn btn-success",
                    })

                }
            },
            errors:function (respond){
                console.log(respond) ;
            }
        })
    }
</script>
</body>
</html>
