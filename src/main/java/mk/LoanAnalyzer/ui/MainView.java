package mk.LoanAnalyzer.ui;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.streams.FileUploadHandler;
import com.vaadin.flow.server.streams.TemporaryFileFactory;
import mk.LoanAnalyzer.service.imprt.XlsxReader;


@Route("")
@PageTitle("Hello, Vaadin!")
public class MainView extends VerticalLayout {
    public MainView() {
        add(new H1("Hello, Vaadin!"));
        Upload upload = new Upload(new FileUploadHandler(
                (mataData, file) -> {
                    XlsxReader reader = null;
                    try {
                        reader = new XlsxReader(file, 0);
                        var line = reader.readLine();
                        System.out.println(line);
                    } finally {
                        if (reader != null) {
                            reader.close();
                        }
                    }
                },
                new TemporaryFileFactory())

        );
        add(upload);
//        upload.setImmediateMode(false);
    }
}
