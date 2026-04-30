import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HitungNilaiServlet", urlPatterns = {"/HitungNilaiServlet"})
public class HitungNilaiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        double hadir     = Double.parseDouble(request.getParameter("hadir"));
        double pertemuan = Double.parseDouble(request.getParameter("pertemuan"));
        double tugas     = Double.parseDouble(request.getParameter("tugas"));
        double uts       = Double.parseDouble(request.getParameter("uts"));
        double uas       = Double.parseDouble(request.getParameter("uas"));

        double nilaiAkhir = (hadir / pertemuan * 100 * 0.1)
                          + (tugas * 0.2)
                          + (uts   * 0.3)
                          + (uas   * 0.4);

        String grade;
        if      (nilaiAkhir >= 80) grade = "A";
        else if (nilaiAkhir >= 70) grade = "B";
        else if (nilaiAkhir >= 60) grade = "C";
        else if (nilaiAkhir >= 50) grade = "D";
        else                       grade = "E";

        String status = nilaiAkhir >= 60 ? "Lulus" : "Tidak Lulus";
        String nilaiAkhirStr = String.format("%.2f", nilaiAkhir);

        request.setAttribute("nilaiAkhir", nilaiAkhirStr);
        request.setAttribute("grade", grade);
        request.setAttribute("status", status);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}