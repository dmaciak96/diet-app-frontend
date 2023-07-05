package pl.daveproject.frontendservice.ui.component;

import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import pl.daveproject.frontendservice.ui.component.type.WebdietNotificationType;

@Slf4j
public class WebdietPhotoFileUpload extends Upload {
    private static final String JPEG_MIME = "image/jpeg";
    private static final String PNG_MIME = "image/png";
    private static final int MAX_FILE_SIZE = 10 * 1024 * 1024;

    public WebdietPhotoFileUpload(Receiver receiver) {
        super(receiver);
        this.setAcceptedFileTypes(JPEG_MIME, PNG_MIME);
        this.setMaxFileSize(MAX_FILE_SIZE);
        this.addClassNames(LumoUtility.Margin.Top.MEDIUM);

        this.addFileRejectedListener(event -> {
            WebdietNotification.show(getTranslation("upload.photo-file-rejected"),
                    WebdietNotificationType.ERROR);
            log.error("File rejected: {}", event.getErrorMessage());
        });
        setupInternationalization();
    }

    private void setupInternationalization() {
        var i18N = new UploadI18N();
        i18N.setDropFiles(new UploadI18N.DropFiles().setOne(getTranslation("upload.drop-one-label"))
                .setMany(getTranslation("upload.drop-many-label")));

        i18N.setAddFiles(new UploadI18N.AddFiles().setOne(getTranslation("upload.select-photo-button-label"))
                .setMany(getTranslation("upload.select-photos-button-label")));

        i18N.setError(new UploadI18N.Error().setTooManyFiles(StringUtils.EMPTY)
                .setFileIsTooBig(getTranslation("upload.photo-file-rejected"))
                .setIncorrectFileType(getTranslation("upload.photo-file-rejected")));

        i18N.setUploading(new UploadI18N.Uploading().setStatus(new UploadI18N.Uploading.Status()
                        .setConnecting(getTranslation("upload.connecting"))
                        .setStalled(getTranslation("upload.stalled"))
                        .setProcessing(getTranslation("upload.processing"))
                        .setHeld(getTranslation("upload.held")))
                .setRemainingTime(new UploadI18N.Uploading.RemainingTime()
                        .setPrefix(getTranslation("upload.remaining-time-prefix"))
                        .setUnknown(getTranslation("upload.remaining-time-unknown")))
                .setError(new UploadI18N.Uploading.Error()
                        .setServerUnavailable(getTranslation("upload.server-unavailable-error"))
                        .setUnexpectedServerError(getTranslation("upload.server-unexpected-error"))
                        .setForbidden(getTranslation("upload.forbidden"))));

        this.setI18n(i18N);
    }
}
