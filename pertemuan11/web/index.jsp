<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="id">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hitung Nilai</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Segoe UI', sans-serif;
        }
        .card {
            border: none;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.4);
            width: 480px;
            background: rgba(255,255,255,0.97);
        }
        .card-header {
            background: linear-gradient(135deg, #11998e, #38ef7d);
            border-radius: 20px 20px 0 0 !important;
            padding: 25px 30px;
            text-align: center;
        }
        .form-control {
            border-radius: 10px;
            border: 2px solid #e0e0e0;
            padding: 10px 15px;
            transition: all 0.3s;
        }
        .form-control:focus {
            border-color: #11998e;
            box-shadow: 0 0 0 0.2rem rgba(17,153,142,0.2);
        }
        .form-control[readonly] {
            background: #f8f9fa;
            font-weight: 600;
            color: #2c5364;
        }
        .btn-hitung {
            background: linear-gradient(135deg, #11998e, #38ef7d);
            border: none;
            border-radius: 10px;
            padding: 12px;
            font-weight: 700;
            color: #fff;
            letter-spacing: 1px;
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .btn-hitung:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(17,153,142,0.4);
            color: #fff;
        }
        .badge-grade {
            font-size: 1.5rem;
            padding: 10px 20px;
            border-radius: 12px;
        }
        .hasil-section {
            background: linear-gradient(135deg, #f8f9fa, #e9ecef);
            border-radius: 12px;
            padding: 15px;
            border-left: 4px solid #11998e;
        }
        .label-col {
            font-weight: 600;
            color: #555;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

<div class="card">
    <div class="card-header">
        <i class="bi bi-calculator-fill text-white" style="font-size: 2.5rem;"></i>
        <h4 class="text-white mt-2 mb-0 fw-bold">Menghitung Nilai</h4>
        <small class="text-white opacity-75">Pemrograman 2 - Dasar Servlet</small>
    </div>

    <div class="card-body p-4">
        <form action="HitungNilaiServlet" method="post">
            <div class="row g-3">

                <div class="col-6">
                    <label class="form-label label-col">
                        <i class="bi bi-person-check me-1 text-success"></i>Jumlah Hadir
                    </label>
                    <input type="number" class="form-control" name="hadir"
                           value="${param.hadir}" placeholder="contoh: 15" min="0" required>
                </div>

                <div class="col-6">
                    <label class="form-label label-col">
                        <i class="bi bi-calendar3 me-1 text-success"></i>Jumlah Pertemuan
                    </label>
                    <input type="number" class="form-control" name="pertemuan"
                           value="${param.pertemuan}" placeholder="contoh: 18" min="1" required>
                </div>

                <div class="col-12">
                    <label class="form-label label-col">
                        <i class="bi bi-journal-check me-1 text-success"></i>Nilai Tugas
                    </label>
                    <input type="number" class="form-control" name="tugas"
                           value="${param.tugas}" placeholder="0 - 100" min="0" max="100" required>
                </div>

                <div class="col-6">
                    <label class="form-label label-col">
                        <i class="bi bi-file-earmark-text me-1 text-success"></i>Nilai UTS
                    </label>
                    <input type="number" class="form-control" name="uts"
                           value="${param.uts}" placeholder="0 - 100" min="0" max="100" required>
                </div>

                <div class="col-6">
                    <label class="form-label label-col">
                        <i class="bi bi-file-earmark-text-fill me-1 text-success"></i>Nilai UAS
                    </label>
                    <input type="number" class="form-control" name="uas"
                           value="${param.uas}" placeholder="0 - 100" min="0" max="100" required>
                </div>

                <%-- Tampilkan hasil jika ada --%>
                <% if (request.getAttribute("nilaiAkhir") != null) { %>
                <div class="col-12 mt-2">
                    <div class="hasil-section">
                        <div class="row align-items-center g-2">
                            <div class="col-7">
                                <div class="mb-2">
                                    <small class="label-col">Nilai Akhir</small>
                                    <input type="text" class="form-control form-control-sm"
                                           readonly value="${nilaiAkhir}">
                                </div>
                                <div class="mb-2">
                                    <small class="label-col">Status</small>
                                    <input type="text" class="form-control form-control-sm"
                                           readonly value="${status}"
                                           style="color: ${status == 'Lulus' ? '#198754' : '#dc3545'}; font-weight:700;">
                                </div>
                            </div>
                            <div class="col-5 text-center">
                                <small class="label-col d-block mb-2">Grade</small>
                                <span class="badge badge-grade
                                    ${grade == 'A' ? 'bg-success' :
                                      grade == 'B' ? 'bg-primary' :
                                      grade == 'C' ? 'bg-warning text-dark' :
                                      grade == 'D' ? 'bg-orange' : 'bg-danger'}">
                                    ${grade}
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
                <% } %>

                <div class="col-12 d-grid mt-2">
                    <button type="submit" class="btn btn-hitung">
                        <i class="bi bi-calculator me-2"></i>HITUNG
                    </button>
                </div>

            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>