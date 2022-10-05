package cleancode.document.v1;

import java.time.LocalDate;

import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
public class DocumentMapper {

    private final AttachmentStore attachmentStore;
    private final AttachmentFactory attachmentFactory;

    @Nullable
    public Document map(@Nonnull final Long userId, @Nonnull final LocalDate date, @Nullable final AttachmentDto attachmentDto) {
        if (nonNull(attachmentDto)) {
            final var storeId = attachmentStore.upload(
                    new ByteArrayInputStream(attachmentDto.fileContent()),
                    MediaType.APPLICATION_PDF_VALUE
            );
            final var attachment = attachmentFactory.create(
                    storeId,
                    attachmentDto.fileName(),
                    MediaType.APPLICATION_PDF_VALUE
            );
            return new Document(userId, date, attachment);
        } else {
            return null;
        }
    }

    public record AttachmentDto(
            byte[] fileContent,
            String fileName
    ) {
    }
}
