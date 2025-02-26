<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Dashboard - SB Admin</title>
                <link href="/admin/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#productFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#productPreview").attr("src", imgURL);
                            $("#productPreview").css({ "display": "block" });
                        });
                    });
                </script>
            </head>

            <body class="sb-nav-fixed">
                <!-- navbar -->
                <jsp:include page="../layout/header.jsp" />
                <!-- end navbar -->
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />

                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Products</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">products</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a product</h3>
                                            <hr />
                                            <form:form method="post" enctype="multipart/form-data"
                                                action="/admin/product/create" modelAttribute="newProduct" class="row">

                                                <c:set var="nameHasBindError">
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="priceHasBindError">
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="detailDescHasBindError">
                                                    <form:errors path="detailDesc" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="shortDescHasBindError">
                                                    <form:errors path="shortDesc" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="quantityHasBindError">
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </c:set>
                                                <div class="mb-3 col-12 col-md-6">

                                                    <label class="form-label">Name:</label>
                                                    <form:input type="text"
                                                        class="form-control  ${not empty nameHasBindError? 'is-invalid':''} "
                                                        path="name" />
                                                    ${nameHasBindError}
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">

                                                    <label class="form-label">Price:</label>
                                                    <form:input type="number"
                                                        class="form-control  ${not empty nameHasBindError? 'is-invalid':''} "
                                                        path="price" />
                                                    ${priceHasBindError}
                                                </div>

                                                <div class="mb-3 col-12 ">

                                                    <label class="form-label">Detail Desc:</label>
                                                    <form:textarea
                                                        class="form-control  ${not empty nameHasBindError? 'is-invalid':''}"
                                                        path="detailDesc" />
                                                    ${detailDescHasBindError}
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">

                                                    <label class="form-label">ShortDesc:</label>
                                                    <form:input type="text"
                                                        class="form-control  ${not empty nameHasBindError? 'is-invalid':''}"
                                                        path="shortDesc" />
                                                    ${shortDescHasBindError}
                                                </div>


                                                <div class="mb-3 col-12 col-md-6">

                                                    <label class="form-label">Quantity:</label>
                                                    <form:input type="number"
                                                        class="form-control  ${not empty nameHasBindError? 'is-invalid':''}"
                                                        path="quantity" />
                                                    ${quantityHasBindError}
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Factory:</label>
                                                    <form:select class="form-select" path="factory">
                                                        <form:option value="APPLE">Apple (MacBook)</form:option>
                                                        <form:option value="ASUS">Asus</form:option>
                                                        <form:option value="LENOVO">Lenovo</form:option>
                                                        <form:option value="DELL">Dell</form:option>
                                                        <form:option value="LG">LG</form:option>
                                                        <form:option value="ACER">Acer</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Target:</label>
                                                    <form:select class="form-select" path="target">
                                                        <form:option value="GAMING">Gaming</form:option>
                                                        <form:option value="SINHVIEN-VANPHONG">Sinh viên - Văn phòng
                                                        </form:option>
                                                        <form:option value="THIET-KE-DO-HOA">Thiết kế đồ họa
                                                        </form:option>
                                                        <form:option value="MONG-NHE">Mỏng nhẹ</form:option>
                                                        <form:option value="DOANH-NHAN">Doanh nhân</form:option>
                                                    </form:select>
                                                </div>


                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="productFile" class="form-label">Image: </label>
                                                    <input class="form-control" type="file" id="productFile"
                                                        accept=".png, .jpg, .jpeg" name="productFile" />
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <img style="max-width: 250px; display: none;"
                                                        alt="productFile Preview" id="productPreview" />
                                                </div>
                                                <div class="mb-3 col-12">
                                                    <button type="submit" class="btn btn-primary">Create</button>
                                                </div>
                                            </form:form>
                                        </div>

                                    </div>

                                </div>
                            </div>
                        </main>
                        <!-- footer -->
                        <jsp:include page="../layout/footer.jsp" />

                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/admin/js/scripts.js"></script>
            </body>

            </html>